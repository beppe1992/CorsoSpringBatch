package it.addvalue.esempio;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {

	@Override
	public void afterJob(JobExecution arg0) {
		System.out.println("Finito Job");

	}

	@Override
	public void beforeJob(JobExecution arg0) {
		System.out.println("Inizio Job");

	}

}
