package it.addvalue.esempio.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
}
