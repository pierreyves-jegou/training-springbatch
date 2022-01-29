package com.example.batch.processor;

import com.example.batch.bean.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
        System.out.println("process : " + person);
        return person;
    }
}
