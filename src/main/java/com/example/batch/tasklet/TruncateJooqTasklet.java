package com.example.batch.tasklet;

import org.jooq.DSLContext;
import org.jooq.generated.tables.Person;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import static org.jooq.generated.Tables.PERSON;
import static org.jooq.generated.Tables.RESULT;

@Component
public class TruncateJooqTasklet implements Tasklet {

    private final DSLContext dslContext;

    public TruncateJooqTasklet(final DSLContext dslContext){
        this.dslContext = dslContext;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        dslContext.truncate(PERSON).execute();
        dslContext.truncate(RESULT).execute();
        return RepeatStatus.FINISHED;
    }
}
