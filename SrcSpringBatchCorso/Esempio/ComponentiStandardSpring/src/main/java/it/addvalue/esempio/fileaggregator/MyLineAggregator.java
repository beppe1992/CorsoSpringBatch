package it.addvalue.esempio.fileaggregator;

import it.addvalue.esempio.generics.InputObject;

import org.springframework.batch.item.file.transform.LineAggregator;

public class MyLineAggregator implements LineAggregator<InputObject> {

	public String aggregate(InputObject inputObj) {
		return inputObj.getNome() + ";" + inputObj.getEta();
	}

}
