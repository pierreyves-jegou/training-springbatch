package com.example.batch.reader.linemapper;

import com.example.batch.bean.Address;
import com.example.batch.bean.Person;
import com.example.batch.bean.PersonOrAddress;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

public class AddressLineMapper implements LineMapperForPattern<PersonOrAddress> {

    LineTokenizer lineTokenizer;

    @Override
    public boolean handle(String line) {
        return line != null && line.startsWith("04");
    }

    @Override
    public PersonOrAddress mapLine(String line, int lineNumber) throws Exception {
        Assert.isTrue(this.lineTokenizer != null, "LineTokenizer is mandatory");
        Address address = new Address();

        FieldSet fieldSet = lineTokenizer.tokenize(line);
        address.setStreetNumber(fieldSet.readInt(0));
        address.setStreet(fieldSet.readString(1));
        address.setTown(fieldSet.readString(2));
        PersonOrAddress personOrAddress = new PersonOrAddress();
        personOrAddress.setAddress(address);
        return personOrAddress;
    }

    public void setLineTokenizer(LineTokenizer lineTokenizer) {
        this.lineTokenizer = lineTokenizer;
    }

}
