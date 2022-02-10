package com.example.batch.config;

import com.example.batch.ArchiveTasklet;
import com.example.batch.FileVerificationSkipper;
import com.example.batch.bean.Person;
import com.example.batch.listener.JobCompletionNotificationListener;
import com.example.batch.reader.JooqPersonCursorReader;
import com.example.batch.reader.JooqPersonReader;
import com.example.batch.reader.PersonReader;
import com.example.batch.processor.PersonProcessor;
import com.example.batch.service.PrometheusService;
import com.example.batch.tasklet.RandomDataTasklet;
import com.example.batch.tasklet.TruncateJooqTasklet;
import com.example.batch.tasklet.TruncateTasklet;
import com.example.batch.writer.BDDPersonWriter;
import com.example.batch.writer.JooqPersonWriter;
import com.example.batch.writer.StepItemWriteListener;
import org.jooq.generated.tables.records.PersonRecord;
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
import org.springframework.beans.factory.annotation.Value;
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
    private TruncateTasklet truncateTasklet;

    @Autowired
    MultiResourceItemReader<Person> multiResourceItemReader;

    @Autowired
    JooqPersonWriter jooqPersonWriter;

    @Autowired
    TruncateJooqTasklet truncateJooqTasklet;


    @Value("${app.chunk-size}")
    Integer chunkSize;

//    @Autowired
//    JooqPersonReader jooqPersonReader;

    @Autowired
    JooqPersonCursorReader personCursorReader;

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobCompletionNotificationListener();
    }



    // Configure job step
    @Bean
    public Job jobBuild() {
        return jobBuilderFactory.get("training-batch").incrementer(new RunIdIncrementer()).listener(jobExecutionListener())
                .incrementer(new RunIdIncrementer())
                .start(stepClean())
                .next(stepInsertRandomData())
                .next(stepBuild())
                .next(stepArchive())
                .build();
    }

    @Autowired
    private RandomDataTasklet randomDataTasklet;


    @Bean
    public Step stepInsertRandomData(){
        return stepBuilderFactory.get("insertRandomDataStep")
                .tasklet(randomDataTasklet)
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
                .tasklet(truncateJooqTasklet)
                .build();
    }

    @Bean
    public Step stepBuild() {
        return stepBuilderFactory.get("Extract -> Transform -> Aggregate -> Load").<PersonRecord, Person> chunk(chunkSize)
                .reader(personCursorReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper())
                .processor(processorBuild())
                .writer(jooqPersonWriter)
                //.faultTolerant()
                //.skip(FlatFileParseException.class)
                //.skipLimit(100)
                .listener(new StepItemWriteListener())
                .build();
    }

    @Autowired
    private PrometheusService prometheusService;

    @Bean
    public ItemProcessor processorBuild() {
        return new PersonProcessor(prometheusService);
    }



    @Bean
    public PersonReader readerBuild(){
        return new PersonReader();
    }


    @Bean
    public SkipPolicy fileVerificationSkipper() {
        return new FileVerificationSkipper();
    }

}
