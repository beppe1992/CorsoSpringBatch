package it.addvalue.example06.processor;

import it.addvalue.example06.SampleSkipException;
import it.addvalue.example06.model.Sample;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessorSkipProcessor extends SampleProcessor implements ItemProcessor<String, Sample>{

	public Sample process(String value){
		Integer id=new Integer(value.substring(5));
		Sample sample=super.process(value);
		//Filter this element
		if(id==45){
			return null;
			//throw new SampleSkipException();
		}
		//Skip this element
		if(id==95){
			throw new SampleSkipException(); 
		}
		return sample;
	}
}
