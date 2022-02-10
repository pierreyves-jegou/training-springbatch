package com.example.batch.writer;

import com.example.batch.bean.Person;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.jooq.generated.tables.Person.PERSON;

@Component
public class JooqPersonWriter implements ItemWriter<Person> {

    private final DSLContext dslContext;

    public JooqPersonWriter(DSLContext dslContext){
        this.dslContext = dslContext;
    }

    @Override
    public void write(List<? extends Person> persons) throws Exception {

        BatchBindStep batchInsert = dslContext.batch(
                dslContext.insertInto(Tables.RESULT,
                PERSON.NAME,
                PERSON.ADDRESS)
                .values((String) null, null)
        );

        persons.forEach(p -> {
            batchInsert.bind(p.getNom(), p.getPrenom());
        });

        batchInsert.execute();

    }
}
