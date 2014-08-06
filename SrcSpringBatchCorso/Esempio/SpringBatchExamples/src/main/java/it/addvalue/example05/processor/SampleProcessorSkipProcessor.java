package it.addvalue.example05.processor;

import it.addvalue.example05.SampleSkipException;
import it.addvalue.example05.model.Sample;
import it.addvalue.example07.SampleRetryException;

import org.springframework.batch.item.ItemProcessor;

public class SampleProcessorSkipProcessor extends SampleProcessor implements ItemProcessor<String, Sample>{

	public Sample process(String value){
		Integer id=new Integer(value.substring(5));
		Sample sample=super.process(value);
		//Filter this element
		if(id==45){
			return null;
		}
		//Retry
		else if(id==65)
		{
			System.out.println("Riprovo");
			throw new SampleRetryException();
		}
		//Skip this element
		else if(id==95){
			throw new SampleSkipException(); 
		}
			
		System.out.println("Process "+id);
		
		return sample;
	}
}
