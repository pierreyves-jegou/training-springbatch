package com.example.batch.reader;

import com.example.batch.bean.PersonOrAddress;
import com.example.batch.reader.linemapper.AddressLineMapper;
import com.example.batch.reader.linemapper.LineMapperForPattern;
import com.example.batch.reader.linemapper.MultiLineLineMapper;
import com.example.batch.reader.linemapper.PersonLineMapper;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonOrAddressFlatFileItemReader extends FlatFileItemReader<PersonOrAddress> {

    public  PersonOrAddressFlatFileItemReader(){
//        Map<String, LineTokenizer> tokenizers = new HashMap<>();
//
//        DelimitedLineTokenizer delimitedLineTokenizerPerson = new DelimitedLineTokenizer();
//
//
//        tokenizers.put("foo*", line -> new DefaultFieldSet(new String[] { "a", "b" }));
//
//        tokenizers.put("bar*", line -> new DefaultFieldSet(new String[] { "c", "d" }));
//        PatternMatchingCompositeLineTokenizer matchingCompositeLineTokenizer = new PatternMatchingCompositeLineTokenizer();
//        matchingCompositeLineTokenizer.setTokenizers(tokenizers);
//
//        Map<String, FieldSetMapper<PersonOrAddress>> fieldSetMappers = new HashMap<>();
//        fieldSetMappers.put("foo*", fs -> new Name(fs.readString(0), fs.readString(1), 0));
//
//
//        MultiLineLineMapper<PersonOrAddress> personOrAddressMultiLineLineMapper = new MultiLineLineMapper<>();
//        personOrAddressMultiLineLineMapper.addLineMapper(new PersonLineMapper());
//        personOrAddressMultiLineLineMapper.addLineMapper(new AddressLineMapper());
//        this.setLineMapper(personOrAddressMultiLineLineMapper);
    }




}
