package com.devcourse.voucherapp.view;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputView implements OutputView {

    @Override
    public <T> void printWithLineBreak(T data) {
        System.out.println(data);
    }

    @Override
    public <T> void printWithoutLineBreak(T data) {
        System.out.print(data);
    }
}
