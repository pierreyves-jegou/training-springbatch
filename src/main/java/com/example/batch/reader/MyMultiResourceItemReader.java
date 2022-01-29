package com.example.batch.reader;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

public class MyMultiResourceItemReader<T> extends MultiResourceItemReader<T> {

    private JobExecution jobExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobExecution = stepExecution.getJobExecution();
        jobExecution.getExecutionContext().put("resourcesKO", new ArrayList<String>());
    }

    public T read() throws Exception, UnexpectedInputException, ParseException {
        try{
            T read = super.read();
            return read;
        }catch(Exception e){
            Resource currentResource = super.getCurrentResource();
            ((List<String>)jobExecution.getExecutionContext().get("resourcesKO")).add(currentResource.getFilename());
            throw e;
        }
    }

}
