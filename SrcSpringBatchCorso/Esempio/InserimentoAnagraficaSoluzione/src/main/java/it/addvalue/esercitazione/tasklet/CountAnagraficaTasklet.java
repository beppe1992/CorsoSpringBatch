package it.addvalue.esercitazione.tasklet;

import it.addvalue.esercitazione.dao.IAnagraficaDao;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Questa tasklet esegue la chiamata del metodo printCountRecordInTabella() del
 * dao iniettato
 * 
 * @author arx50036
 * 
 */
public class CountAnagraficaTasklet implements Tasklet, InitializingBean {

	private IAnagraficaDao dao;

	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		System.out.println("Eseguo la tasklet");
		System.out.println("chiamo il DAO");
		dao.printCountRecordInTabella();

		return RepeatStatus.FINISHED;
	}

	public void setDao(IAnagraficaDao dao) {
		this.dao = dao;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dao, "Inietta un IAnagraficaDao non null");
	}

}
