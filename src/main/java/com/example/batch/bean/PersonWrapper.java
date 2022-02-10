package com.example.batch.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonWrapper {

    private Person person;
    private List<Address> addressList = new ArrayList<>();

}
