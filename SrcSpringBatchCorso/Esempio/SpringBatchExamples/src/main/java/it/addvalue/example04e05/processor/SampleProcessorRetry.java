package it.addvalue.example04e05.processor;

import it.addvalue.example04e05.SampleSkipException;
import it.addvalue.example04e05.model.Sample;
import it.addvalue.example06.SampleRetryException;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessorRetry implements ItemProcessor<String, Sample> {

	public Sample process(String value) {
		Integer id = new Integer(value.substring(5));

		Sample example = new Sample();
		example.setId(Long.parseLong(id + ""));
		example.setData(value);
		example.setType(1);

		// Retry
		if (id == 65) {
			System.out.println("Riprovo");
			throw new SampleRetryException();
		}

		System.out.println("Process " + id);

		return example;
	}
}
