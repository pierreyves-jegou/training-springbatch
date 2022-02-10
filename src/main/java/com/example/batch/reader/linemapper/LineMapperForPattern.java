package com.example.batch.reader.linemapper;

import org.springframework.batch.item.file.LineMapper;

public interface LineMapperForPattern<T> extends LineMapper<T> {

    boolean handle(String line);

}
