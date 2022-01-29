package com.example.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    public void afterJob(JobExecution jobExecution) {
        System.out.println("after job");
    }

    public void beforeJob(JobExecution jobExecution) {
        System.out.println("before job");
    }

}
