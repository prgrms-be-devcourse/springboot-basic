package com.programmers.voucher.io;

import org.springframework.stereotype.Component;

@Component
public class Console<T> {
    Output output;

    public Console(Output output) {
        this.output = output;
    }

    public void printOutput(T message) {
        output.printOutput(message);
    }
}
