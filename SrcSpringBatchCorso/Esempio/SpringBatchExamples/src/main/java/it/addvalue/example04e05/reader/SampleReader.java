package it.addvalue.example04e05.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;

public class SampleReader implements ItemReader<String>, InitializingBean {

	String[] array = new String[100];

	public void afterPropertiesSet() throws Exception {
		for (int i = 0; i < 100; i++) {
			array[i] = new String("Name:" + i);
		}

	}

	static int readIndex = -1;

	public String read() {
		
		readIndex++;
		if (readIndex >= array.length - 1) {
			readIndex = -1;
			return null;
		}
		return array[readIndex];
	}

}
