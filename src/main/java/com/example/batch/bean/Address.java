package com.example.batch.bean;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class Address {

    Integer streetNumber;
    String street;
    String town;

}
