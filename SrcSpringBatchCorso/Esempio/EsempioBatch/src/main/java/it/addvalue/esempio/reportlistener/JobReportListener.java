package it.addvalue.esempio.reportlistener;

import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;

/**
 * Listener che nell'afterJob salva nella tabella BATCH_JOB_EXECUTION_CONTEXT un report di esecuzione del Job e stampa il contenuto di JOB_CONTEXT_MAP
 * se presente in JobExecution <br/> <b>Utilizzabile con versione di spring batch fino alla 2.X.X</b>
 * @author Simone Laico by AddValue S.p.A.
 * @version 14/09/2012
 */
public class JobReportListener extends AbstractReportListener
{

    @Override
    public Map<String, JobParameter> getParameters(JobExecution jobExecution)
    {

        return jobExecution.getJobInstance().getJobParameters().getParameters();
    }

}
