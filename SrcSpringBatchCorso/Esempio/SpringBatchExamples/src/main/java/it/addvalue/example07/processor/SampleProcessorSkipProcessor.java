package it.addvalue.example07.processor;

import it.addvalue.example07.SampleRetryException;
import it.addvalue.example07.SampleSkipException;
import it.addvalue.example07.model.Sample;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessorSkipProcessor extends SampleProcessor implements ItemProcessor<String, Sample>{

	public Sample process(String value){
		Integer id=new Integer(value.substring(5));
		Sample sample=super.process(value);
		//Filter this element
		if(id==45){
			return null;
		}
		
		// se decommento viene rilanciata la retry policy
//		if(id==95){
//			throw new SampleRetryException(); 
//		}
		
		//Skip this element
		if(id==95){
			throw new SampleSkipException(); 
		}
		return sample;
	}
}
