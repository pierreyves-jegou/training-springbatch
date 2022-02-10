package com.example.batch.reader;

import com.example.batch.bean.PersonOrAddress;
import com.example.batch.bean.PersonWrapper;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;

public class MultiLineItemReader extends SingleItemPeekableItemReader<PersonWrapper> {

    private FlatFileItemReader<PersonOrAddress> flatFileItemReader;





}
