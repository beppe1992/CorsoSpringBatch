package it.addvalue.esempio;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class MyTasklet implements Tasklet {

	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		System.out.println("");
		System.out.println("print tasklet");
		return RepeatStatus.FINISHED;
	}
}
