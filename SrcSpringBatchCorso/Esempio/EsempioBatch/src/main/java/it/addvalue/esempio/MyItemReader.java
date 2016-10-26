package it.addvalue.esempio;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class MyItemReader implements ItemReader<MyObject>,
		ItemStreamReader<MyObject> {

	private int i = 1;

	public MyObject read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		System.out.println("");
		System.out.println("****************************");
		System.out.println("READER");
		System.out.println("****************************");
		System.out.println("");

		MyObject object = new MyObject();

		if (i > 6) {
			return null;
		}

		object.setNome("reader " + i);
		i++;
		System.out.println(object.getNome());

		return object;
	}

	public void open(ExecutionContext executionContext)
			throws ItemStreamException {
		System.out.println();
		System.out.println("open reader");

	}

	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
		System.out.println();
		System.out.println("update reader");

	}

	public void close() throws ItemStreamException {
		System.out.println("close reader");

	}

}
