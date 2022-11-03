package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

@Component
public class Console {
    private final Input input;
    private final Output output;


    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void printText(String text) {
        output.outputText(text);
    }
}
