package com.example.batch.reader.linemapper;

import com.example.batch.bean.PersonOrAddress;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class MultiLineLineMapper<T> implements LineMapper<T> {

    private List<LineMapperForPattern<T>> lineMapperForPatterns = new ArrayList<>();
    private LineTokenizer lineTokenizer;

    @Override
    public T mapLine(String line, int lineNumber) throws Exception {
        FieldSet fieldSet = lineTokenizer.tokenize(line);

        Assert.isTrue(this.lineMapperForPatterns.size() > 0, "A least one delegating lineMapper should be set");
        return this.lineMapperForPatterns.stream()
                .filter(lineMapper -> lineMapper.handle(line))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("No lineMapper can handle this line : %s", line)))
                .mapLine(line, lineNumber);
    }

    public void addLineMapper(LineMapperForPattern<T> lineMapper){
        this.lineMapperForPatterns.add(lineMapper);
    }
}
