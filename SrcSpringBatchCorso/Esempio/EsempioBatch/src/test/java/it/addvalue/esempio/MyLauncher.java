package it.addvalue.esempio;

import java.security.SecureRandom;

import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
@ContextConfiguration(locations = { "classpath:EsempioBatch.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class MyLauncher {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	private JobParameters jobParameters;

	private JobParametersBuilder builder = null;

	@Before
	public void init() {
		builder = new JobParametersBuilder().addString("JobId", "EsempioBatch");
		jobParameters = builder.toJobParameters();
	}

	@Test
	public void executeJob() {
		try {
			JobExecution jobExecution = this.jobLauncher.run(this.job,
					jobParameters);
			Assert.assertThat(jobExecution.getExitStatus(),
					Matchers.is(ExitStatus.COMPLETED));
		} catch (Throwable ex) {
			String message = "Job terminated in error: " + ex.getMessage();
			System.err.printf("%s (%s)%n", message, ex);
		}
	}

}
