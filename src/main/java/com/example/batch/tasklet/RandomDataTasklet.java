package com.example.batch.tasklet;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.jooq.generated.tables.records.PersonRecord;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import static org.jooq.generated.tables.Person.PERSON;

@Component
public class RandomDataTasklet implements Tasklet {

    private final DSLContext dslContext;

    public RandomDataTasklet(DSLContext dslContext){
        this.dslContext = dslContext;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.excludeField(FieldPredicates
                .named("id").and(FieldPredicates.inClass(PersonRecord.class)));

        int i=0;
        do {
            EasyRandom generator = new EasyRandom(parameters);
            generator.setSeed(123L);
            dslContext.insertInto(Tables.PERSON, PERSON.NAME, PERSON.ADDRESS)
                    .values(generator.nextObject(String.class), generator.nextObject(String.class))
                    .execute();

            i++;
        }while(i < 20);



        return RepeatStatus.FINISHED;
    }
}
