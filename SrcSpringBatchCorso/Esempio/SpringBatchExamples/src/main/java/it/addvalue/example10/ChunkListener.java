package it.addvalue.example10;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ChunkListener implements
		org.springframework.batch.core.ChunkListener {

	public void beforeChunk(ChunkContext chunkContext) {
		System.out.println("----------- BEFORE CHUNK -----------");
	}

	public void afterChunk(ChunkContext chunkContext) {
		System.out.println("----------- AFTER CHUNK -----------");
	}

	public void afterChunkError(ChunkContext chunkContext) {
		System.out.println("----------- AFTER CHUNK ERROR -----------");
	}
}
