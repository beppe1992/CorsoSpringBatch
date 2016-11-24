package it.addvalue.example09;

import org.springframework.batch.item.file.transform.LineAggregator;

public class SimpleCsvLineAggregator implements LineAggregator<TableToRead> {

	public String aggregate(TableToRead item) {
		return item.getId() + ";" + item.getNome() + ";" + item.getCognome()
				+ ";" + item.getCodiceFiscale() + ";"
				+ item.getRagioneSociale();
	}

}