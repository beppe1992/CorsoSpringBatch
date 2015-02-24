package it.addvalue.esempio;

import java.security.SecureRandom;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe base astratta per i test d'integrazione.
 * 
 * @author Nicol&oacute; Tacconi - addvalue
 */
public abstract class BaseIntegrationTest
{

    @Autowired
    private JobLauncher          jobLauncher;

    @Autowired
    private Job                  job;

    private JobParameters        jobParameters;

    private JobParametersBuilder builder = null;

    protected void init(String key,
                        String parameter)
    {
        builder = new JobParametersBuilder().addString(key, parameter.concat(String.valueOf(new SecureRandom().nextLong())));
        setJobParameters(builder.toJobParameters());
    }

    public JobParametersBuilder getBuilder()
    {

        return builder;
    }

    /**
     * Esegue il job e stampa l'eventuale eccezzione
     */
    public void executeJob()
    {
        try
        {
            JobExecution jobExecution = this.jobLauncher.run(this.job, getJobParameters());
            Assert.assertThat(jobExecution.getExitStatus(), Matchers.is(ExitStatus.COMPLETED));
        }
        catch ( Throwable ex )
        {
            String message = "Job terminated in error: " + ex.getMessage();
            System.err.printf("%s (%s)%n", message, ex);
        }
    }

    /**
     * Esegue il job e ne ritorna l'ExitStatus
     * 
     * @return l'exit status del job
     * @throws JobExecutionAlreadyRunningException
     *             Nel caso in cui il job sia gia' in esecuzione
     * @throws JobRestartException
     *             Nel caso in cui il job non sia restartabile (con i parameters indicati)
     * @throws JobInstanceAlreadyCompleteException
     *             Nel caso in cui la stessa istanza di esecuzione sia gia' stata completata con successo
     * @throws JobParametersInvalidException
     *             Nel caso in cui i parametri non siano corretti
     */
    public ExitStatus executeJobReturnExitStatus() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
    {
        JobExecution jobExecution = this.jobLauncher.run(this.job, getJobParameters());
        return jobExecution.getExitStatus();
    }

    /**
     * Esegue il job e ne ritorna il JobExecution
     * 
     * @return il JobExecution del job
     * @throws JobExecutionAlreadyRunningException
     *             Nel caso in cui il job sia gia' in esecuzione
     * @throws JobRestartException
     *             Nel caso in cui il job non sia restartabile (con i parameters indicati)
     * @throws JobInstanceAlreadyCompleteException
     *             Nel caso in cui la stessa istanza di esecuzione sia gia' stata completata con successo
     * @throws JobParametersInvalidException
     *             Nel caso in cui i parametri non siano corretti
     */
    public JobExecution executeJobReturnJobExecutionAndPrintError() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
    {
        return jobLauncher.run(this.job, getJobParameters());
    }

    /**
     * Esegue il job e ne ritorna il JobExecution
     * 
     * @return il JobExecution del job
     * @throws JobExecutionAlreadyRunningException
     *             Nel caso in cui il job sia gia' in esecuzione
     * @throws JobRestartException
     *             Nel caso in cui il job non sia restartabile (con i parameters indicati)
     * @throws JobInstanceAlreadyCompleteException
     *             Nel caso in cui la stessa istanza di esecuzione sia gia' stata completata con successo
     * @throws JobParametersInvalidException
     *             Nel caso in cui i parametri non siano corretti
     */
    public JobExecution executeJobReturnJobExecution() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
    {
        return jobLauncher.run(this.job, getJobParameters());
    }

    public JobParameters getJobParameters()
    {
        return jobParameters;
    }

    public void setJobParameters(JobParameters jobParameters)
    {
        this.jobParameters = jobParameters;
    }

}
