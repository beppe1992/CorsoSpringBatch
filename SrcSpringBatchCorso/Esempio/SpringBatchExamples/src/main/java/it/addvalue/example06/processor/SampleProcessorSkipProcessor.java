package it.addvalue.example06.processor;

import it.addvalue.example06.SampleRetryException;
import it.addvalue.example06.SampleSkipException;
import it.addvalue.example06.model.Sample;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessorSkipProcessor implements
		ItemProcessor<String, Sample> {

	public Sample process(String value) {
		Integer id = new Integer(value.substring(5));

		Sample example = new Sample();
		example.setData(value);
		example.setType(1);
		example.setId(Long.parseLong(id + ""));

		// se decommento viene rilanciata la retry policy
		// if(id==95){
		// throw new SampleRetryException();
		// }

		// Skip this element
		if (id == 95) {
			throw new SampleSkipException();
		}
		return example;
	}
}
