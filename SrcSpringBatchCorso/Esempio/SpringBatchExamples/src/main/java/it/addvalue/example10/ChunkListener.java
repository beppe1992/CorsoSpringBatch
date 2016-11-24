package it.addvalue.example10;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;

public class ChunkListener implements
		org.springframework.batch.core.ChunkListener {

	public void beforeChunk() {
		System.out.println("----------- BEFORE CHUNK -----------");

	}

	public void afterChunk() {
		System.out.println("----------- AFTER CHUNK -----------");

	}

}
