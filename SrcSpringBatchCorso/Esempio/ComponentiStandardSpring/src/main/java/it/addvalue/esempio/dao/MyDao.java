package it.addvalue.esempio.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class MyDao extends JdbcDaoSupport {

	public List<String> recuperaTuttiLibri() {
		return null;
	}

	public void inserisciSuDb(String libro) {

	}

	@Autowired
	public MyDao(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

}
