package com.programmers.voucher.io;

import org.springframework.stereotype.Component;

@Component
public class Output<T> {
    public void printOutput(T message) {
        System.out.println(message);
    }
}
