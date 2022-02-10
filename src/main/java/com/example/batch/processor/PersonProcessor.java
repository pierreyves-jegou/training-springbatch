package com.example.batch.processor;

import com.example.batch.bean.Person;
import com.example.batch.service.PrometheusService;
import org.jooq.generated.tables.records.PersonRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PersonProcessor implements ItemProcessor<PersonRecord, Person> {

    private final PrometheusService prometheusService;

    public PersonProcessor(PrometheusService prometheusService){
        this.prometheusService = prometheusService;
    }


    @Override
    public Person process(PersonRecord person) throws Exception {
        prometheusService.addMetrics("myProcessor");
        System.out.println("process : " + person);
        Person p = new Person();
        p.setNom(person.getName());
        p.setPrenom(person.getAddress());
        return p;
    }
}
