package it.addvalue.esempio;

import org.springframework.batch.item.ItemProcessor;

public class MyItemProcessor implements ItemProcessor<MyObject, MyObject> {

	int i = 1;

	public MyObject process(MyObject item) throws Exception {

		System.out.println("");
		System.out.println("****************************");
		System.out.println("PROCESSOR");
		System.out.println("****************************");
		System.out.println("");

		System.out.println("processor " + i);
		item.setNome("writer " + i);
		i++;

		return item;
	}

}
