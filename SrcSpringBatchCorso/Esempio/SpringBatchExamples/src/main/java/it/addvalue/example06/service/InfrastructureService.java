package it.addvalue.example06.service;

import org.springframework.batch.core.JobExecution;

public interface InfrastructureService {

	public void generateReport(JobExecution jobExecution);
}
