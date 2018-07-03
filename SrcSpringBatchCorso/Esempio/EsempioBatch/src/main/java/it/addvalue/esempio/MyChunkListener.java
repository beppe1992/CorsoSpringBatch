package it.addvalue.esempio;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class MyChunkListener implements ChunkListener {

	@Override
	public void afterChunk() {
		System.out.println("");
		System.out.println("chunk listener - after chunk");

	}

	@Override
	public void beforeChunk() {
		System.out.println("");
		System.out.println("chunk listener - before chunk");

	}
}
