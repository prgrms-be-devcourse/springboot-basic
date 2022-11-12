package com.programmers.voucher.io;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput<T> implements Output<T> {
    @Override
    public void printOutput(T message) {
        System.out.println(message);
    }
}
