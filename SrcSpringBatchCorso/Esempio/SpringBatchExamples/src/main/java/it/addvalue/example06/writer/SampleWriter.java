package it.addvalue.example06.writer;

import it.addvalue.example06.BaseElement;
import it.addvalue.example06.dao.SampleDao;
import it.addvalue.example06.model.Sample;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class SampleWriter extends BaseElement implements ItemWriter<Sample> {

	StringBuilder sb = new StringBuilder();
	private SampleDao sampleDao;
	
	public void write(List<? extends Sample> items) throws Exception {
		for(Sample exemple: items){
			//Integer id=new Integer(exemple.getData().substring(5));
			sampleDao.save(exemple);
		}
	}
	
	public SampleDao getSampleDao() {
		return sampleDao;
	}

	public void setSampleDao(SampleDao sampleDao) {
		this.sampleDao = sampleDao;
	}
}

