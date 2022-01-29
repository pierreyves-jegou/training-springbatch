package com.example.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

public class ArchiveTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<String> resourcesKO = (List<String>)chunkContext.getStepContext().getJobExecutionContext().get("resourcesKO");

        System.out.println("OK");
        return RepeatStatus.FINISHED;
    }


}
