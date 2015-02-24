package it.addvalue.esercitazione.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * L'implementazione dell'interfaccia {@link IAnagraficaDao}.
 * 
 * @author arx50036
 * 
 */
public class AnagraficaDao implements IAnagraficaDao {

	private JdbcTemplate jdbcTemplate;

	public void printCountRecordInTabella() {
		System.out.println("Il numero di record presenti in tabella e' "
				+ getJdbcTemplate().queryForInt(
						"SELECT COUNT(*) FROM ANAGRAFICA"));
	}

	// METODO DA UTILIZZARE PER SETTARE IL DATASOURCE
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
