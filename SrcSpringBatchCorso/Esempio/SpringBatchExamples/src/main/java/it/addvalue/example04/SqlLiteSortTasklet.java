package it.addvalue.example04;

import it.addvalue.example04.impl.dao.SqlLiteDBSortDao;
import it.addvalue.example04.interfaces.dao.InRamDBSortDao;
import it.addvalue.example04.interfaces.key.SortingKeyGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class SqlLiteSortTasklet<T> implements Tasklet, InitializingBean
{
    private static final String REQUIRED_FIELDS_MESSAGE = "You must set the reader/writer elements";

    private ItemReader<T>       reader;

    private ItemWriter<T>       writer;

    private SortingKeyGenerator keyGenerator;

    private DataSource          dataSource;

    public void afterPropertiesSet() throws Exception
    {
        Assert.notNull(reader, REQUIRED_FIELDS_MESSAGE);
        Assert.notNull(writer, REQUIRED_FIELDS_MESSAGE);
        Assert.notNull(keyGenerator, REQUIRED_FIELDS_MESSAGE);
        Assert.notNull(dataSource, REQUIRED_FIELDS_MESSAGE);
    }

    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception
    {
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

        checkOpen(reader, executionContext);
        checkOpen(writer, executionContext);

        T item = null;
        
        InRamDBSortDao<T> dao = new SqlLiteDBSortDao<T>(dataSource, "APP1_");
        
        List<T> items = new ArrayList<T>();
        
        while ((item = reader.read()) != null)
        {
            items.add(item);
            
            if(items.size() >= 1000)
            {
                dao.insertRowToSort(items, keyGenerator);
                items.clear();
            }
//            dao.insertRowToSort(keyGenerator.getKey(item), item);
        }
        dao.rowInsertionComplete();
        System.out.println("readed");
   
        dao.extractAndWriteRows(writer, 1000);

        System.out.println("writed");
        dao.clear();

        System.out.println("dao cleared");

        checkClose(reader);
        checkClose(writer);
        
        return RepeatStatus.FINISHED;
    }
    
    private void checkOpen(Object obj,
                           ExecutionContext executionContext)
    {

        if ( obj instanceof ItemStream )
        {
            ItemStream itemStream = (ItemStream) obj;
            itemStream.open(executionContext);
        }
        if ( obj instanceof ChunkListener )
        {
            ChunkListener chunkListener = (ChunkListener) obj;
            chunkListener.beforeChunk();
        }

    }

    private void checkClose(Object obj)
    {

        if ( obj instanceof ItemStream )
        {
            ItemStream itemStream = (ItemStream) obj;
            itemStream.close();
        }

        if ( obj instanceof ChunkListener )
        {
            ChunkListener chunkListener = (ChunkListener) obj;
            chunkListener.afterChunk();
        }
    }

    public void setReader(ItemReader<T> reader)
    {
        this.reader = reader;
    }

    public void setWriter(ItemWriter<T> writer)
    {
        this.writer = writer;
    }

    public void setKeyGenerator(SortingKeyGenerator keyGenerator)
    {
        this.keyGenerator = keyGenerator;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

}
