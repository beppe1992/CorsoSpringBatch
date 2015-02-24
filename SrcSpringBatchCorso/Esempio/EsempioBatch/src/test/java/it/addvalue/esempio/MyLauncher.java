package it.addvalue.esempio;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Test d'integrazione dell'intero job in Ram
 * 
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
		super.init("JobId", "JSTPAGScadPolSostituiteAA");

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test
	public void run() throws Exception {

//		System.out.println();
//		System.out.println("numero di record "
//				+ jdbcTemplate.queryForInt("SELECT COUNT(*) FROM MY_TABELLA"));
//		System.out.println();

		executeJob();

		// System.out.println();
		// System.out.println("numero di record "
		// + jdbcTemplate.queryForInt("SELECT COUNT(*) FROM MY_TABELLA"));
		// System.out.println();
	}

}
