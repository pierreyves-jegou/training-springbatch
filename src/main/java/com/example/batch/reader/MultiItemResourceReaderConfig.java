package com.example.batch.reader;

import com.example.batch.bean.Person;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultiItemResourceReaderConfig {

    @Value("input*.csv")
    private Resource[] inputResources;

    @Bean
    public MultiResourceItemReader<Person> multiResourceItemReader()
    {
        PersonReader personReader = new PersonReader();
        MyMultiResourceItemReader<Person> myMultiResourceItemReader = new MyMultiResourceItemReader();
        myMultiResourceItemReader.setResources(inputResources);
        myMultiResourceItemReader.setDelegate(personReader);
        return myMultiResourceItemReader;

//        MultiResourceItemReader<Person> resourceItemReader = new MultiResourceItemReader<>();
//        resourceItemReader.setResources(inputResources);
//        resourceItemReader.setDelegate(personReader);
//
//        return resourceItemReader;
    }

}
