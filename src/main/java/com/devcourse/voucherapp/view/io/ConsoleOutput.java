package com.devcourse.voucherapp.view.io;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput implements Output {

    @Override
    public <T> void printWithLineBreak(T data) {
        System.out.println(data);
    }

    @Override
    public <T> void printWithoutLineBreak(T data) {
        System.out.print(data);
    }
}
