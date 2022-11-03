package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

@Component
public class Console extends IO {
    private final Input input;
    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void doOutput(String text) {
        output.printText(text);
    }

    public String getInput() {
        return input.inputText();
    }
}
