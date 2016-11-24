package it.addvalue.example09;

import org.springframework.batch.item.ItemProcessor;

public class CodiceFiscaleMapper implements
		ItemProcessor<TableToRead, TableToRead> {

	public TableToRead process(TableToRead item) throws Exception {

		item.setCodiceFiscale("GAGSATASAS" + item.getNome() + item.getCognome());

		return item;
	}
}
