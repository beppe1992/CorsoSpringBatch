package it.addvalue.example07.processor;

import it.addvalue.example07.model.Sample;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessor implements ItemProcessor<String, Sample>{

	public Sample process(String value){
		Sample exemple=new Sample();
		exemple.setData(value);
		exemple.setType(1);
		return exemple;
	}
}
