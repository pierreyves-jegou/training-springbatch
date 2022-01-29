package com.example.batch.writer;

import com.example.batch.bean.Person;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class PersonWriter implements ItemWriter<Person> {

    @Override
    public void write(List<? extends Person> list) throws Exception {
        System.out.println(list);
    }
}
