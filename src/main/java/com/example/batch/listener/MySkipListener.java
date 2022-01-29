package com.example.batch.listener;

import com.example.batch.bean.Person;
import com.example.batch.service.ResourceFollower;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MySkipListener implements SkipListener<Person, Person> {

    @Autowired
    ResourceFollower resourceFollower;

    @Override
    public void onSkipInRead(Throwable throwable) {
        System.out.println("yes");
       // resourceFollower.pu
    }

    @Override
    public void onSkipInWrite(Person person, Throwable throwable) {
        System.out.println("yes");
    }

    @Override
    public void onSkipInProcess(Person person, Throwable throwable) {
        System.out.println("yes");
    }
}
