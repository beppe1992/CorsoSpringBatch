package it.addvalue.example06.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


public class BaseDao {

	protected JdbcTemplate simpleJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
	    this.simpleJdbcTemplate = new JdbcTemplate(dataSource);
	}
	
}
