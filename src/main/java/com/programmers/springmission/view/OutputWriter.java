package com.programmers.springmission.view;

import java.util.List;

public class OutputWriter implements Output {

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void write(List<?> responses) {
        for (Object response : responses) {
            System.out.println(response);
        }
    }
}

