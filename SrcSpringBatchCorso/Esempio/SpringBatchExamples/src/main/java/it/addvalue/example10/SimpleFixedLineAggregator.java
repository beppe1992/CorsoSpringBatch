package it.addvalue.example10;

import org.springframework.batch.item.file.transform.LineAggregator;

public class SimpleFixedLineAggregator implements LineAggregator<TableToRead> {

	public String aggregate(TableToRead item) {
		return item.getId() + item.getNome() + item.getCognome()
				+ item.getCodiceFiscale() + item.getRagioneSociale();
	}

}