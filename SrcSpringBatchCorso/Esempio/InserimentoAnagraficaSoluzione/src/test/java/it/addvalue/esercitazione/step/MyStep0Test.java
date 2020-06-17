package it.addvalue.esercitazione.step;

import it.addvalue.esercitazione.dto.Anagrafica;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test per lo step0
 * 
 * @author Nicol&oacute; Tacconi - addvalue
 * 
 */
@ContextConfiguration(locations = { "classpath:InserimentoAnagraficaJobTest.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
// @DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class MyStep0Test {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	private final String stepName = "myStep0";

	List<Anagrafica> anagrafiche;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);

		deleteAll();
		checkNumberOfRecordInAnagraficaTable(0);

		anagrafiche = new ArrayList<Anagrafica>();

		Anagrafica anagrafica = new Anagrafica();
		anagrafica.setCodiceFiscale("CODICEFIS1");
		anagrafica.setNome("NOME1");
		anagrafica.setCognome("COGNOME1");

		anagrafiche.add(anagrafica);

		anagrafica = new Anagrafica();
		anagrafica.setCodiceFiscale("CODICEFIS2");
		anagrafica.setNome("NOME2");
		anagrafica.setCognome("COGNOME2");

		anagrafiche.add(anagrafica);

		anagrafica = new Anagrafica();
		anagrafica.setCodiceFiscale("CODICEFIS3");
		anagrafica.setNome("NOME3");
		anagrafica.setCognome("COGNOME3");

		anagrafiche.add(anagrafica);

		jdbcTemplate
				.batchUpdate(
						"INSERT INTO ANAGRAFICA (CODICE_FISCALE,NOME,COGNOME) VALUES (?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								Anagrafica anagrafica = anagrafiche.get(i);
								ps.setString(1, anagrafica.getCodiceFiscale());
								ps.setString(2, anagrafica.getNome());
								ps.setString(3, anagrafica.getCognome());

							}

							public int getBatchSize() {
								return anagrafiche.size();
							}
						});

		// controllo di aver caricato 3 record
		checkNumberOfRecordInAnagraficaTable(3);
	}

	@After
	public void after() {
		deleteAll();
	}

	@Test
	public void noDaoSetTest() {
		// eseguo lo step con 3 record in tabella
		JobExecution jobExecution = jobLauncherTestUtils.launchStep(stepName);
		Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}

	private void deleteAll() {
		jdbcTemplate.update("DELETE FROM ANAGRAFICA");
	}

	private void checkNumberOfRecordInAnagraficaTable(Integer numeroDiRecord) {
		Assert.assertEquals(numeroDiRecord,
				jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ANAGRAFICA",Integer.class));
	}
}
