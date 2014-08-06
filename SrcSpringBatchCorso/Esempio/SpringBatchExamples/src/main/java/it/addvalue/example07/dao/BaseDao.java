package it.addvalue.example07.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class BaseDao {

	protected SimpleJdbcTemplate simpleJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
	    this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
}
