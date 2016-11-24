package it.addvalue.example10;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;

public class ProcessListener implements
		ItemProcessListener<TableToRead, TableToRead> {

	public void beforeProcess(TableToRead item) {
		System.out.println("----------- BEFORE PROCESS -----------");

	}

	public void afterProcess(TableToRead item, TableToRead result) {
		System.out.println("----------- AFTER PROCESS -----------");

	}

	public void onProcessError(TableToRead item, Exception e) {
		// TODO Auto-generated method stub

	}

}
