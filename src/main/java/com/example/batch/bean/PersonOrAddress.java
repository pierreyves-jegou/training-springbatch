package com.example.batch.bean;

import org.springframework.data.util.Pair;

public class PersonOrAddress {

    Pair<Person, Address> eitherValue;

    public void setPerson(Person person){
        eitherValue = Pair.of(person, null);
    }

    public void setAddress(Address address){
        eitherValue = Pair.of(null, address);
    }

}
