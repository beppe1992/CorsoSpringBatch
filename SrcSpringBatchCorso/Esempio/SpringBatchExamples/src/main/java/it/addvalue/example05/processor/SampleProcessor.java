package it.addvalue.example05.processor;

import it.addvalue.example05.model.Sample;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessor implements ItemProcessor<String, Sample>{

	public Sample process(String value){
		
		Sample exemple=new Sample();
		exemple.setData(value);
		exemple.setType(1);
		return exemple;
	}
}
