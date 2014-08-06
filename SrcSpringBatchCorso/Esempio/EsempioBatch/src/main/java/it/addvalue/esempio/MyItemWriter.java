package it.addvalue.esempio;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;

public class MyItemWriter implements ItemWriter<MyObject>,
		ItemStreamWriter<MyObject> {

	int i = 0;

	public void write(List<? extends MyObject> items) throws Exception {

		System.out.println("writer " + i);
		System.out.println("La lista che è arrivata nel witer contiene "
				+ items.size() + " elementi");
		System.out.println();
		i++;

	}

	public void open(ExecutionContext executionContext)
			throws ItemStreamException {
		System.out.println("open writer");
	}

	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
		System.out.println("update writer");
	}

	public void close() throws ItemStreamException {
		System.out.println("close writer");
	}

}
