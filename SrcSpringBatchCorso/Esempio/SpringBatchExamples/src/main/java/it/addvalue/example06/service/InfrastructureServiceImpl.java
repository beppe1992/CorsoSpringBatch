package it.addvalue.example06.service;

import java.util.Iterator;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

public class InfrastructureServiceImpl implements InfrastructureService{

	public void generateReport(JobExecution jobExecution){
		Iterator<StepExecution> stepExecutions=jobExecution.getStepExecutions().iterator();
		while(stepExecutions.hasNext()){
			StepExecution stepExecution=stepExecutions.next();
			System.out.println("StepName:"+stepExecution.getStepName());
			System.out.println("   ReadCount:"+stepExecution.getReadCount());
			System.out.println("   WriteCount:"+stepExecution.getWriteCount());
			System.out.println("   SkipCount:"+stepExecution.getSkipCount());
			System.out.println("");
		}
	}

}
