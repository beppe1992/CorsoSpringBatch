package it.addvalue.example04e05.writer;

import it.addvalue.example04e05.dao.SampleDao;
import it.addvalue.example04e05.model.Sample;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class SampleWriter implements ItemWriter<Sample> {

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

