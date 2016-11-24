package it.addvalue.example10;

import org.springframework.batch.core.ItemReadListener;

public class ReadListener implements ItemReadListener<TableToRead> {

	public void beforeRead() {
		System.out.println("----------- BEFORE READ -----------");
		
	}

	public void afterRead(TableToRead item) {
		System.out.println("----------- AFTER READ -----------");
		
	}

	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		
	}

}
