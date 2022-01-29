package com.example.batch.writer;

import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class StepItemWriteListener implements ItemWriteListener<Number> {

    @Override
    public void beforeWrite(List<? extends Number> items) {
        System.out.println("ItemWriteListener - beforeWrite");
    }

    @Override
    public void afterWrite(List<? extends Number> items) {
        System.out.println("ItemWriteListener - afterWrite");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Number> items) {
        System.out.println("ItemWriteListener - onWriteError");
    }
}
