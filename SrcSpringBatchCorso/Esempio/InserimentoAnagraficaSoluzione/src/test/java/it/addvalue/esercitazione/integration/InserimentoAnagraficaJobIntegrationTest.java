package it.addvalue.esercitazione.integration;

import it.addvalue.esercitazione.BaseIntegrationTest;
import it.addvalue.esercitazione.dto.Anagrafica;

import java.util.List;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test d'integrazione dell'intero job in Ram
 * 
 * @author Nicol&oacute; Tacconi - addvalue
 */
@ContextConfiguration(locations = { "classpath:InserimentoAnagraficaJobTest.xml" })
// @DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class InserimentoAnagraficaJobIntegrationTest extends
		BaseIntegrationTest {

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
		// testo l'esecuzione della catena nella sua interezza
		// ATTENZIONE, TUTTI I PRINT NON SONO NECESSARI AL TEST MA ERANO UTILI
		// IN FASE DI SVILUPPO
		printAnagrafica(selectAllFromAnagrafica());

		System.out.println("");
		System.out.println();
		System.out.println();
		System.out
				.println("****************************************************");
		System.out.println("Inizio JOB");
		System.out.println();
		System.out.println();

		// ***************************************************************************
		// Lancio della catena
		ExitStatus exitStatus = executeJobReturnExitStatus();
		assertEquals(ExitStatus.COMPLETED, exitStatus);

		System.out.println("");
		System.out.println();
		System.out.println();
		System.out
				.println("****************************************************");
		System.out.println("Fine JOB");
		System.out.println();
		System.out.println();

		// *********************************************************
		// Inizio test
		List<Anagrafica> anagraficheInTabella = selectAllFromAnagrafica();
		printAnagrafica(anagraficheInTabella);

		// verifico che in tabella siano presenti 3 record
		assertEquals(
				"Il numero di record in tabella e' errato!!!! Ce ne devono essere solamente 3",
				3, anagraficheInTabella.size());
		Anagrafica anagrafica = anagraficheInTabella.get(0);
		assertThat(anagrafica.getCodiceFiscale(), is("EZIADT24SDF23CA"));
		assertThat(anagrafica.getNome(), is("Ezio"));
		assertThat(anagrafica.getCognome(), is("Auditore"));

		anagrafica = anagraficheInTabella.get(1);
		assertThat(anagrafica.getCodiceFiscale(), is("GSPGDN92A15939U"));
		assertThat(anagrafica.getNome(), is("Giuseppe"));
		assertThat(anagrafica.getCognome(), is("Giordano"));

		anagrafica = anagraficheInTabella.get(2);
		assertThat(anagrafica.getCodiceFiscale(), is("TCCNCL91C14L949O"));
		assertThat(anagrafica.getNome(), is("Nicolo"));
		assertThat(anagrafica.getCognome(), is("Tacconi"));

	}

	private List<Anagrafica> selectAllFromAnagrafica() {
		return jdbcTemplate.query(
				"SELECT * FROM ANAGRAFICA ORDER BY CODICE_FISCALE ASC",
				new Object[] {}, new BeanPropertyRowMapper<Anagrafica>(
						Anagrafica.class));
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
