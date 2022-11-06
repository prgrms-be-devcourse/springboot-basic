package com.programmers.voucher.io;

import org.springframework.stereotype.Component;

@Component
public class Console<T> {
    Input input;
    Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input.input();
    }

    public void printOutput(T message) {
        output.printOutput(message);
    }
}
