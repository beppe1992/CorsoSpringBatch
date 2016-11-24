package it.addvalue.example09;

import org.springframework.batch.item.ItemProcessor;

public class RagioneSocialeMapper implements
		ItemProcessor<TableToRead, TableToRead> {

	public TableToRead process(TableToRead item) throws Exception {
		
		
		item.setRagioneSociale(item.getNome() + " " + item.getCognome());

		return item;
	}
}
