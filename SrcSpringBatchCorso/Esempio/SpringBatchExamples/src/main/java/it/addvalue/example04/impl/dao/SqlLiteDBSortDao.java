package it.addvalue.example04.impl.dao;

import it.addvalue.example04.interfaces.dao.InRamDBSortDao;
import it.addvalue.example04.interfaces.key.SortingKeyGenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.repository.dao.AbstractJdbcBatchMetadataDao;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;

public class SqlLiteDBSortDao<T> extends AbstractJdbcBatchMetadataDao implements InitializingBean, InRamDBSortDao<T>
{

    private static final String INSERT_ROW  = "INSERT INTO %PREFIX%SORT_INRAM(JOB_SORT_KEY, JOB_CONTENT) VALUES (?, ?)";

    private static final String SELECT_ROWS = "SELECT JOB_SORT_KEY, JOB_CONTENT FROM %PREFIX%SORT_INRAM ORDER BY JOB_SORT_KEY ASC";

    private static final String CLEAR_ROWS  = "DELETE FROM %PREFIX%SORT_INRAM";

    private final LobHandler    lobHandler  = new DefaultLobHandler();

    public SqlLiteDBSortDao(DataSource dataSource,
                            String tablePrefix)
    {
        setJdbcTemplate(new SimpleJdbcTemplate(dataSource));
        setTablePrefix(tablePrefix);
    }

    public void insertRowToSort(final String key,
                                final T obj)
    {
        ByteArrayOutputStream bStream = null;
        ObjectOutputStream oStream = null;

        if ( obj == null )
            return;

        try
        {
            bStream = new ByteArrayOutputStream();
            oStream = new ObjectOutputStream(bStream);
            oStream.writeObject(obj);
            oStream.flush();
            final byte[] byteVal = bStream.toByteArray();

            getJdbcTemplate().getJdbcOperations().execute(getQuery(INSERT_ROW), new AbstractLobCreatingPreparedStatementCallback(lobHandler)
            {
                protected void setValues(PreparedStatement ps,
                                         LobCreator lobCreator) throws SQLException, DataAccessException
                {
                    ps.setString(1, key);
                    lobCreator.setBlobAsBytes(ps, 2, byteVal);
                }
            });
        }
        catch ( Exception ex )
        {
            throw new IllegalArgumentException("Can't serialize object.", ex);
        }
        finally
        {
            try
            {
                oStream.close();
                bStream.close();
            }
            catch ( IOException e )
            {
            }
        }
    }

    public void insertRowToSort(final List<T> items,
                                final SortingKeyGenerator keyGenerator)
    {
        try
        {
            getJdbcTemplate().getJdbcOperations().batchUpdate(getQuery(INSERT_ROW), new BatchPreparedStatementSetter()
            {
                public void setValues(PreparedStatement ps,
                                      int i) throws SQLException
                {
                    ByteArrayOutputStream bStream = null;
                    ObjectOutputStream oStream = null;

                    try
                    {
                        bStream = new ByteArrayOutputStream();
                        oStream = new ObjectOutputStream(bStream);
                        oStream.writeObject(items.get(i));
                        oStream.flush();
                        byte[] byteVal = bStream.toByteArray();

                        ps.setString(1, keyGenerator.getKey(items.get(i)));
                        lobHandler.getLobCreator().setBlobAsBytes(ps, 2, byteVal);
                    }
                    catch ( Exception ex )
                    {

                    }
                    finally
                    {
                        try
                        {
                            oStream.close();
                            bStream.close();
                        }
                        catch ( IOException e )
                        {
                        }
                    }
                }

                public int getBatchSize()
                {
                    return items.size();
                }
            });
        }
        catch ( Exception ex )
        {
            throw new IllegalArgumentException("Can't serialize object.", ex);
        }

    }

    /*
     * (non-Javadoc)
     * @see it.addvalue.np.batch.utilities.sort.dao.InRamDBSortDaoe#extractSortedRows()
     */
    public List<T> extractSortedRows()
    {
        final List<T> ret = new ArrayList<T>();

        try
        {
            Object[] parameters = new Object[]
            {};

            getJdbcTemplate().getJdbcOperations().query(getQuery(SELECT_ROWS), parameters, new RowCallbackHandler()
            {
                public void processRow(ResultSet rs) throws SQLException
                {
                    byte[] obj = lobHandler.getBlobAsBytes(rs, 2);

                    ByteArrayInputStream bis = null;
                    ObjectInputStream ois = null;
                    try
                    {
                        bis = new ByteArrayInputStream(obj);
                        ois = new ObjectInputStream(bis);
                        Object readObject = ois.readObject();
                        ret.add((T) readObject);
                    }
                    catch ( IOException e )
                    {
                        throw new IllegalArgumentException("Can't get object from DB.", e);
                    }
                    catch ( ClassNotFoundException e )
                    {
                        throw new IllegalArgumentException("Can't get object from DB.", e);
                    }
                    finally
                    {
                        try
                        {
                            ois.close();
                            bis.close();
                        }
                        catch ( IOException e )
                        {
                        }
                    }
                }
            });
        }
        catch ( Exception ex )
        {
            throw new IllegalArgumentException("Can't get objects from DB.", ex);
        }

        return ret;
    }

    public void clear()
    {
        getJdbcTemplate().getJdbcOperations().execute(getQuery(CLEAR_ROWS));
    }

    public void extractAndWriteRows(final ItemWriter<T> writer,
                                    final int bufferSize)
    {
        final List<T> ret = new ArrayList<T>();

        try
        {
            Object[] parameters = new Object[]
            {};

            getJdbcTemplate().getJdbcOperations().query(getQuery(SELECT_ROWS), parameters, new RowCallbackHandler()
            {
                public void processRow(ResultSet rs) throws SQLException
                {
                    byte[] obj = lobHandler.getBlobAsBytes(rs, 2);

                    ByteArrayInputStream bis = null;
                    ObjectInputStream ois = null;
                    try
                    {
                        bis = new ByteArrayInputStream(obj);
                        ois = new ObjectInputStream(bis);
                        Object readObject = ois.readObject();
                        ret.add((T) readObject);

                        if ( ret.size() > bufferSize )
                        {
                            writer.write(ret);
                            ret.clear();
                        }
                    }
                    catch ( IOException e )
                    {
                        throw new IllegalArgumentException("Can't get object from DB.", e);
                    }
                    catch ( ClassNotFoundException e )
                    {
                        throw new IllegalArgumentException("Can't get object from DB.", e);
                    }
                    catch ( Exception e )
                    {
                        throw new IllegalArgumentException("Can't write data to file.", e);
                    }
                    finally
                    {
                        try
                        {
                            ois.close();
                            bis.close();
                        }
                        catch ( IOException e )
                        {
                        }
                    }
                }
            });
        }
        catch ( Exception ex )
        {
            throw new IllegalArgumentException("Can't get objects from DB.", ex);
        }
    }

    public void rowInsertionComplete()
    {

    }

}