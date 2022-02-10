package com.example.batch.linetokenizer;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class PersonOrAddressTokenizer implements LineTokenizer {

    @Override
    public FieldSet tokenize(String s) {
        return null;
    }
}
