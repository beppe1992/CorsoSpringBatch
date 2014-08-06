package it.addvalue.esercitazione;

import it.addvalue.esercitazione.dto.Anagrafica;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.arca.danni.utils.springbatch.integrationtest.BaseIntegrationTest;

/**
 * Test d'integrazione dell'intero job in Ram
 * Pluto
 * @author Nicol&oacute; Tacconi - addvalue
 */
@ContextConfiguration(locations = { "classpath:myJob.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
// @Ignore
public class MyLauncher extends BaseIntegrationTest {

	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void init() {
		super.init("JobId", "inserimentoAnagraficaJob");

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test
	public void run() throws Exception {

		printAnagrafica(selectAllFromAnagrafica());

		System.out.println("");
		System.out.println();
		System.out.println();
		System.out
				.println("****************************************************");
		System.out.println("Inizio JOB");
		System.out.println();
		System.out.println();

		ExitStatus exitStatus = executeJobReturnExitStatus();
		Assert.assertEquals(ExitStatus.COMPLETED, exitStatus);

		System.out.println("");
		System.out.println();
		System.out.println();
		System.out
				.println("****************************************************");
		System.out.println("Fine JOB");
		System.out.println();
		System.out.println();

		List<Anagrafica> anagraficheInTabella = selectAllFromAnagrafica();
		printAnagrafica(anagraficheInTabella);

		Assert.assertEquals("Il numero di record in tabella e' errato!!!! Ce ne devono essere solamente 3",3, anagraficheInTabella.size());
	}

	private List<Anagrafica> selectAllFromAnagrafica() {
		return jdbcTemplate.query("SELECT * FROM ANAGRAFICA", new Object[] {},
				new BeanPropertyRowMapper<Anagrafica>(Anagrafica.class));
	}

	private void printAnagrafica(List<Anagrafica> anagrafiche) {
		System.out.println();
		System.out.println("TABELLA ANAGRAFICA");
		System.out.println("------------------------------------");
		System.out.print("NOME");
		System.out.print("  |  ");
		System.out.print("COGNOME");
		System.out.print("  |  ");
		System.out.print("CODICE_FISCALE");
		System.out.println("  |");
		System.out.println("-----------------------------------------");

		for (Anagrafica anagrafica : anagrafiche) {
			System.out.print(anagrafica.getNome());
			System.out.print("  |  ");
			System.out.print(anagrafica.getCognome());
			System.out.print("  |  ");
			System.out.print(anagrafica.getCodiceFiscale());
			System.out.print("  |");
			System.out.println();
		}

	}

}
