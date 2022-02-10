package com.example.batch.reader.linemapper;

import com.example.batch.bean.Person;
import com.example.batch.bean.PersonOrAddress;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class PersonLineMapper implements LineMapperForPattern<PersonOrAddress> {

    LineTokenizer lineTokenizer;

    @Override
    public PersonOrAddress mapLine(String line, int lineNumber) throws Exception {
        Person person = new Person();

        FieldSet fieldSet = lineTokenizer.tokenize(line);
        person.setNom(fieldSet.readString(0));
        person.setPrenom(fieldSet.readString(1));
        PersonOrAddress personOrAddress = new PersonOrAddress();
        personOrAddress.setPerson(person);
        return personOrAddress;
    }

    @Override
    public boolean handle(String line) {
        return line != null && line.startsWith("03");
    }
}
