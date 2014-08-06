package it.addvalue.esempio;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class MyStepListener implements StepExecutionListener {

	public void beforeStep(StepExecution stepExecution) {
		System.out.println("");
		System.out.println("step listener");

	}

	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("");
		System.out.println("after lister");
		System.out.println("status " + stepExecution.getExitStatus());
		return stepExecution.getExitStatus();
	}

}
