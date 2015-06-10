package it.addvalue.esempio.reportlistener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Listener che nell'afterJob salva nella tabella BATCH_JOB_EXECUTION_CONTEXT un report di esecuzione del Job e stampa il
 * contenuto di JOB_CONTEXT_MAP se presente in JobExecution
 * @author Simone Laico by AddValue S.p.A.
 * @version 14/09/2012
 */
public abstract class AbstractReportListener implements JobExecutionListener, ApplicationContextAware,
InitializingBean
{

    private static final Logger           LOG     = Logger.getLogger(AbstractReportListener.class);

    private static final int              LUNG_10 = 10;

    private static final int              LUNG_12 = 12;

    private static final int              LUNG_19 = 19;

    private static final int              LUNG_50 = 50;

    private static final SimpleDateFormat SDF     = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static final String           LINE    = "------------------------------------------------------------------------------------------------------------------------------\n";

    private static final String           MODEL   = "\nL''istanza {5} della catena ''{0}'' si � conclusa con esito: {11}\n\nDETTAGLI SERVER\n"
                                                    + "Server name:          {1} ({2})\nServer thread:        {3}\n\nDETTAGLI CATENA\nJobName:"
                                                    + "              {0}\nJob-instance-id:      {5}\nJob-execution-id:    "
                                                    + " {4}\nTimeStart:            {6}\nTimeEnd:              {7}\n\n{10}"
                                                    + "\nDETTAGLIO DEGLI STEP ESEGUITI\n{8}\n\n{9}\n\n";

    private JobRepository                 jobRepository;

    private ApplicationContext            applicationContext;

    @Override
    public void beforeJob(JobExecution jobExecution)
    {
        // nulla da fare
    }

    @Override
    public void afterJob(JobExecution jobExecution)
    {
        try
        {
            JobInstance jobInstance = jobExecution.getJobInstance();

            String nomeCatena = jobInstance.getJobName();
            String hostName = InetAddress.getLocalHost().getHostName();
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            String threadId = String.valueOf(Thread.currentThread().getId());
            String jobExecutionId = String.valueOf(jobExecution.getId());
            String jobInstanceId = String.valueOf(jobInstance.getId());

            String startDate = null;
            String endDate = null;

            synchronized ( AbstractReportListener.class )
            {
                startDate = SDF.format(jobExecution.getStartTime());
                endDate = SDF.format(jobExecution.getEndTime());
            }

            String stepsDescriptionTable = this.getStepsDescriptionTable(jobInstance, jobExecution);
            String errorStackTrace = this.getErrorStackTrace(jobExecution);
            String parametersList = this.getParametersList(jobInstance, jobExecution);
            String executionOutcome = jobExecution.getExitStatus().getExitCode();

            String report = MessageFormat.format(MODEL,
                                                 nomeCatena,
                                                 hostName,
                                                 ipAddress,
                                                 threadId,
                                                 jobExecutionId,
                                                 jobInstanceId,
                                                 startDate,
                                                 endDate,
                                                 stepsDescriptionTable,
                                                 errorStackTrace,
                                                 parametersList,
                                                 executionOutcome);

            jobExecution.getExecutionContext().put("report", report);

            this.jobRepository.updateExecutionContext(jobExecution);
        }
        catch ( Exception e )
        {
            LOG.error(e);
        }
    }

    private String getErrorStackTrace(JobExecution jobExecution)
    {
        StringBuilder errorDetail = new StringBuilder();

        if ( jobExecution.getAllFailureExceptions().size() > 0 )
        {
            errorDetail.append("ERRORI:\n");
        }
        else
        {
            errorDetail.append("NESSUN ERRORE.\n");
        }

        for ( Throwable t : jobExecution.getAllFailureExceptions() )
        {
            errorDetail.append(getStackTraceAsString(t));
            errorDetail.append("\n\n");
            errorDetail.append(LINE);
        }
        return errorDetail.toString();
    }

    /**
     * Metodo che recupera i parametri in base alla versione che si sta utilizzando Dalla JobInstance se si utilizza la versione
     * 2.1.8, se superiore si recupera dalla JobExecution
     * @param jobExecution
     *            oggetto JobExecution SpringBatch
     * @return la mappa dei parametri
     */
    public abstract Map<String, JobParameter> getParameters(JobExecution jobExecution);

    private String getParametersList(JobInstance jobInstance,
                                     JobExecution jobExecution)
    {
        StringBuilder parameterBuilder = new StringBuilder();
        boolean first = true;
        Map<String, JobParameter> parameters = getParameters(jobExecution);

        for ( Entry<String, JobParameter> entry : parameters.entrySet() )
        {

            if ( first )
            {
                first = false;
                parameterBuilder.append("Parameters:           ");
            }
            else
            {
                parameterBuilder.append("                      ");

            }
            parameterBuilder.append(entry.getKey());
            parameterBuilder.append("=");
            parameterBuilder.append(entry.getValue().toString());
            parameterBuilder.append("\n");
        }
        return parameterBuilder.toString();
    }

    private String getStepsDescriptionTable(JobInstance jobInstance,
                                            JobExecution jobExecution)
    {

        StringBuilder tableBuilder = new StringBuilder();

        tableBuilder.append(LINE);
        this.writeSimgleRow(tableBuilder, "Exit-Code", "Step-Id", "Step-name", "Start-time", "End-time");
        tableBuilder.append(LINE);

        for ( StepExecution se : jobExecution.getStepExecutions() )
        {
            this.writeMultiRow(tableBuilder, se);
        }
        tableBuilder.append(LINE);

        return tableBuilder.toString();

    }

    private void writeMultiRow(StringBuilder tableBuilder,
                               StepExecution se)
    {
        String stepStartTime = null;
        String stepEndTime = null;

        synchronized ( AbstractReportListener.class )
        {
            stepStartTime = SDF.format(se.getStartTime());
            stepEndTime = SDF.format(se.getEndTime());
        }

        this.writeSimgleRow(tableBuilder,
                            se.getExitStatus().getExitCode(),
                            "" + se.getId(),
                            se.getStepName(),
                            stepStartTime,
                            stepEndTime);
        String info1 = "  --> Letti (" + se.getReadCount() + "), Scartati (" + se.getSkipCount()
                       + "), Scritti (" + se.getWriteCount() + ")";
        String info2 = "  --> ChunksEseguiti (" + se.getCommitCount() + "), Rollbacks ("
                       + se.getRollbackCount() + ")";
        this.writeSimgleRow(tableBuilder, "", "", info1, "", "");
        this.writeSimgleRow(tableBuilder, "", "", info2, "", "");
        this.writeSimgleRow(tableBuilder, "", "", "", "", "");

    }

    private void writeSimgleRow(StringBuilder tableBuilder,
                                String string,
                                String string2,
                                String string3,
                                String string4,
                                String string5)
    {
        tableBuilder.append(String.format("  %1$-" + LUNG_12 + "s | %2$-" + LUNG_10 + "s | %3$-" + LUNG_50
                                          + "s | %4$-" + LUNG_19 + "s | %5$-" + LUNG_19 + "s%n",
                                          string,
                                          string2,
                                          string3,
                                          string4,
                                          string5));
    }

    private static String getStackTraceAsString(Throwable error)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, false);
        String trace = "";

        try
        {
            error.printStackTrace(pw);
            pw.flush();
            sw.flush();
            trace = sw.toString();
        }
        catch ( Exception e )
        {
            // never occurs
            LOG.error(e);
        }
        finally
        {
            try
            {
                sw.close();
                pw.close();
            }
            catch ( Exception e )
            {
                // never occurs
                LOG.error(e);
            }
        }

        return trace;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        this.jobRepository = this.applicationContext.getBean(JobRepository.class);

    }

}
