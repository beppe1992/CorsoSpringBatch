package it.addvalue.example08;

import org.springframework.batch.item.file.transform.LineAggregator;

public class SimpleTestLineAggregator implements LineAggregator<TableToRead> {

	public String aggregate(TableToRead item) {
		return item.getId() + ";" + item.getNome() + ";" + item.getCognome();
	}

}