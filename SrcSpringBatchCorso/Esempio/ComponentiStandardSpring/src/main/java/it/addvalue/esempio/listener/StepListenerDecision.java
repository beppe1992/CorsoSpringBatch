package it.addvalue.esempio.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * StepExecutionListener per lo step verificaProposta. Ritorna lo status
 * INVIA_MAIL solamente nel caso in cui l'ExitStatus risulti in stato COMPLETED
 * e nel contesto dello step sia presente la chiave che si chiami INVIA_MAIL con
 * valore a true. In tutti gli altri casi non influisce sull'ExitStatus
 * originario.
 * 
 * @author Nicol&oacute; Tacconi - Addvalue
 */
public class StepListenerDecision implements StepExecutionListener {

	public void beforeStep(StepExecution stepExecution) {

	}

	public ExitStatus afterStep(StepExecution stepExecution) {
		if (!stepExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {

			return new ExitStatus("INVIA_MAIL",
					"Step Completato, invio la mail");
		}
		return null;
	}

}
