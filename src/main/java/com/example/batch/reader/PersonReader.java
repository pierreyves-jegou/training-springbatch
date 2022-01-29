package com.example.batch.reader;

import com.example.batch.bean.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class PersonReader extends FlatFileItemReader<Person> {

    public PersonReader(){
        //Set input file
        //this.setResource(new ClassPathResource("input.csv"));
        //Skip the file header line
        this.setLinesToSkip(1);
        //Line is mapped to item (FxMarketEvent) using setLineMapper(LineMapper)
        this.setLineMapper(new DefaultLineMapper<Person>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer(";") {
                    {
                        setNames(new String[] { "ligne", "nom", "prenom" });
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
                    {
                        setTargetType(Person.class);
                    }
                });
            }
        });
    }

}
