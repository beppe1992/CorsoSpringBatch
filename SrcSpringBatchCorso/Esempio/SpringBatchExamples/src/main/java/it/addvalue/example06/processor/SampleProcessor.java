package it.addvalue.example06.processor;

import it.addvalue.example06.BaseElement;
import it.addvalue.example06.model.Sample;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessor extends BaseElement implements ItemProcessor<String, Sample>{

	public Sample process(String value){
		Sample exemple=new Sample();
		exemple.setData(value);
		exemple.setType(1);
		return exemple;
	}
}
