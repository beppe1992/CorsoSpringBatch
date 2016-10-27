package it.addvalue.esempio.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class DatePartitioner implements Partitioner {

	private int dataDa;

	private int dataA;

	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> ret = new HashMap<String, ExecutionContext>();

		for (int i = dataDa; i <= dataA; i++) {

			ExecutionContext context = new ExecutionContext();

			context.put("FILTER", i);

			ret.put(String.format("partition_%d", i), context);
		}

		return ret;
	}

	public void setDataDa(int dataDa) {
		this.dataDa = dataDa;
	}

	public void setDataA(int dataA) {
		this.dataA = dataA;
	}

}
