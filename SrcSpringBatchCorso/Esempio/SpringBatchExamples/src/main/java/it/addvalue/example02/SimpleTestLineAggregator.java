package it.addvalue.example02;

import org.springframework.batch.item.file.transform.LineAggregator;

public class SimpleTestLineAggregator implements
		LineAggregator<SimpleTestValueObject> {

	public String aggregate(SimpleTestValueObject item) {
		return item.getName() + ";" + item.getVal() + ";" + item.getSurname();
	}
}