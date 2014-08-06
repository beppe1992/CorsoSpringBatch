package it.addvalue.example06;

import it.addvalue.example06.service.InfrastructureService;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class SkippedItemReportStep implements Tasklet {
       
	private InfrastructureService infrastructureService;
	
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		infrastructureService.generateReport(chunkContext.getStepContext().getStepExecution().getJobExecution());
        return RepeatStatus.FINISHED;
	}

	public InfrastructureService getInfrastructureService() {
		return infrastructureService;
	}

	public void setInfrastructureService(InfrastructureService infrastructureService) {
		this.infrastructureService = infrastructureService;
	}
       
} 