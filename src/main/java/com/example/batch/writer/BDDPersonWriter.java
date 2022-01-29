package com.example.batch.writer;

import com.example.batch.bean.Person;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class BDDPersonWriter implements ItemWriter<Person> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(List<? extends Person> list) throws Exception {
        list.forEach(person -> {
//            if(person.getNom().equals("rrr")){
//                throw new RuntimeException("my exception");
//            }
            entityManager.merge(person);
            entityManager.flush();
        });
    }
}
