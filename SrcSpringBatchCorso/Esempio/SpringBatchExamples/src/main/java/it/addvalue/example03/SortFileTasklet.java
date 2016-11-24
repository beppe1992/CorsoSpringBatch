package it.addvalue.example03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class SortFileTasklet<T> implements Tasklet, InitializingBean {

	private static final String REQUIRED_FIELDS_MESSAGE = "You must set the reader/writer elements";

	private List<SimpleTestValueObject> listToSort;

	private ItemWriter<SimpleTestValueObject> writer;

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(writer, REQUIRED_FIELDS_MESSAGE);
	}

	@SuppressWarnings("unchecked")
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		ExecutionContext executionContext = chunkContext.getStepContext()
				.getStepExecution().getExecutionContext();

		Collections.sort((List<SimpleTestValueObject>) listToSort,
				new Comparator<SimpleTestValueObject>() {

					public int compare(SimpleTestValueObject o1,
							SimpleTestValueObject o2) {

						if (o1 == null && o2 == null)
							return 0;
						else if (o1 == null && o2 != null)
							return 1;
						else if (o1 != null && o2 == null)
							return -1;
						else
							return o1.getName().compareTo(o2.getName());
					}
				});

		System.out.println("Sorting collection... DONE");

		checkOpenStream(writer, executionContext);

		checkOpenListener(writer);

		writer.write(listToSort);

		checkCloseListener(writer);

		checkCloseStream(writer);

		System.out.println("writed " + listToSort.size() + " items (total)");

		return RepeatStatus.FINISHED;
	}

	private void checkOpenStream(Object obj, ExecutionContext executionContext) {
		if (obj instanceof ItemStream) {
			ItemStream itemStream = (ItemStream) obj;
			itemStream.open(executionContext);
		}
	}

	private void checkOpenListener(Object obj) {
		if (obj instanceof ChunkListener) {
			ChunkListener chunkListener = (ChunkListener) obj;
			chunkListener.beforeChunk();
		}
	}

	private void checkCloseStream(Object obj) {
		if (obj instanceof ItemStream) {
			ItemStream itemStream = (ItemStream) obj;
			itemStream.close();
		}
	}

	private void checkCloseListener(Object obj) {
		if (obj instanceof ChunkListener) {
			ChunkListener chunkListener = (ChunkListener) obj;
			chunkListener.afterChunk();
		}
	}

	public void setListToSort(List<SimpleTestValueObject> listToSort) {
		this.listToSort = listToSort;
	}

	public void setWriter(ItemWriter<SimpleTestValueObject> writer) {
		this.writer = writer;
	}

}