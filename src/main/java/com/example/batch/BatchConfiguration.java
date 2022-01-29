package com.example.batch;

import com.example.batch.bean.Person;
import com.example.batch.listener.JobCompletionNotificationListener;
import com.example.batch.reader.PersonReader;
import com.example.batch.processor.PersonProcessor;
import com.example.batch.tasklet.TruncateTasklet;
import com.example.batch.writer.BDDPersonWriter;
import com.example.batch.writer.StepItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BDDPersonWriter bddPersonWriter;

    @Autowired
    private TruncateTasklet truncateTasklet;

    @Autowired
    MultiResourceItemReader<Person> multiResourceItemReader;

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobCompletionNotificationListener();
    }


    // Configure job step
    @Bean
    public Job jobBuild() {
        return jobBuilderFactory.get("FxMarket Prices ETL Job").incrementer(new RunIdIncrementer()).listener(jobExecutionListener())
                .incrementer(new RunIdIncrementer())
                .start(stepClean())
                .next(stepBuild())
                .next(stepArchive())
                .build();
    }

    @Bean
    public Step stepArchive(){
        return stepBuilderFactory.get("archive")
                .tasklet(new ArchiveTasklet())
                .build();
    }

    @Bean
    public Step stepClean(){
        return stepBuilderFactory.get("clean")
                .tasklet(truncateTasklet)
                .build();
    }

    @Bean
    public Step stepBuild() {
        return stepBuilderFactory.get("Extract -> Transform -> Aggregate -> Load").<Person, Person> chunk(2)
                .reader(multiResourceItemReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper())
                .processor(processorBuild())
                .writer(writerBuild())
                //.faultTolerant()
                //.skip(FlatFileParseException.class)
                //.skipLimit(100)
                .listener(new StepItemWriteListener())
                .build();
    }

    @Bean
    public ItemProcessor processorBuild() {
        return new PersonProcessor();
    }



    @Bean
    public PersonReader readerBuild(){
        return new PersonReader();
    }

    @Bean
    public BDDPersonWriter writerBuild(){
        return bddPersonWriter;
    }

    @Bean
    public SkipPolicy fileVerificationSkipper() {
        return new FileVerificationSkipper();
    }

}
