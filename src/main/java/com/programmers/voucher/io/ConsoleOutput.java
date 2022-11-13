package com.programmers.voucher.io;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput implements Output {
    @Override
    public void printOutput(String message) {
        System.out.println(message);
    }
}
