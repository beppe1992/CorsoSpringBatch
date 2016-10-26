package it.addvalue.esempio.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class MyDao extends JdbcDaoSupport {

	public String recuperaUnRecord(String input) {
		return super.getJdbcTemplate().queryForObject(
				"SELECT * FROM TABELLA WHERE NOME = ?", String.class, input);
	}

	public void inserisciSuDb(String libro) {
		super.getJdbcTemplate().update("INSERT INTO TABELLA VALUES(?)", libro);
	}
	
	public void inserisciSuDb(final List<String> libri) {
		super.getJdbcTemplate().batchUpdate("INSERT INTO TABELLA VALUES(?)", new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				String libro = libri.get(i);
				
				ps.setString(0, libro);
				
			}
			
			public int getBatchSize() {
				return libri.size();
			}
		});
	}
}
