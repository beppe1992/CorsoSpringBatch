package it.addvalue.example04e05.processor;

import it.addvalue.example04e05.SampleSkipException;
import it.addvalue.example04e05.model.Sample;
import it.addvalue.example06.SampleRetryException;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessorSkip implements ItemProcessor<String, Sample> {

	public Sample process(String value) {
		Integer id = new Integer(value.substring(5));

		Sample example = new Sample();
		example.setId(Long.parseLong(id + ""));
		example.setData(value);
		example.setType(1);

		// Filter this element
		if (id == 45) {
			return null;
		}
		// Skip this element
		else if (id == 95) {
			throw new SampleSkipException();
		}

		System.out.println("Process " + id);

		return example;
	}
}
