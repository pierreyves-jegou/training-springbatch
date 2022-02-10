package com.example.batch.reader;

import com.example.batch.service.PrometheusService;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.jooq.generated.tables.records.PersonRecord;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.jooq.generated.tables.Person.PERSON;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class JooqPersonReader implements ItemReader<PersonRecord> {

    private final DSLContext dslContext;
    private final PrometheusService prometheusService;
    List<PersonRecord> collect;

    public JooqPersonReader(DSLContext dslContext, PrometheusService prometheusService){
        this.dslContext = dslContext;
        this.prometheusService = prometheusService;
    }

    @Override
    public PersonRecord read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        prometheusService.addMetrics("myFirstCounter");

        if(collect == null){
            collect = this.dslContext.select(PERSON.ID, PERSON.NAME, PERSON.ADDRESS)
                    .from(Tables.PERSON)
                    .fetch()
                    .stream()
                    .map(rec -> this.map((Integer) rec.get(PERSON.ID), rec.get(PERSON.NAME), rec.get(PERSON.ADDRESS)))
                    .collect(Collectors.toList());
        }

        if(collect.size() > 0){
            Random rand = new Random();
            Integer randomDuration = rand.nextInt(2000);
            Thread.sleep(randomDuration.longValue());
            return collect.remove(collect.size()-1);
        }else{
            return null;
        }


    }

    private PersonRecord map(Integer id, String name, String address){
        return new PersonRecord().values(id, name, address);
    }

}
