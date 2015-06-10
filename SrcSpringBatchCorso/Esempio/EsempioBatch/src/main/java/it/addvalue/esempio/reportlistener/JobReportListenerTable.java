package it.addvalue.esempio.reportlistener;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;

/**
 * Classe utile a scrivere la tabella di riepilogo nei log
 * @author Giuseppe Giordano - Addavalue
 */
public class JobReportListenerTable extends JobReportListener
{

    private static Logger log = Logger.getLogger(JobReportListenerTable.class);

    @Override
    public void afterJob(JobExecution jobExecution)
    {

        super.afterJob(jobExecution);
        log.info(jobExecution.getExecutionContext().get("report"));
    }
}
