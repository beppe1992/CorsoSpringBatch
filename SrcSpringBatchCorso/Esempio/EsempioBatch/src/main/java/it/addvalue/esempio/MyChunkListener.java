package it.addvalue.esempio;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class MyChunkListener implements ChunkListener {



	@Override
	public void beforeChunk(ChunkContext chunkContext) {
		System.out.println("");
		System.out.println("chunk listener - before chunk");
	}

	@Override
	public void afterChunk(ChunkContext chunkContext) {
		System.out.println("");
		System.out.println("chunk listener - after chunk");
	}

	@Override
	public void afterChunkError(ChunkContext chunkContext) {
		System.out.println("");
		System.out.println("chunk listener - after chunk error");
	}
}
