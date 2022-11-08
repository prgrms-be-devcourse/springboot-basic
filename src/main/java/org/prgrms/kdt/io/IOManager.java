package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

@Component
public class IOManager {
    private final Console console;

    public IOManager(Console console) {
        this.console = console;
    }

    public String getInput() {
        return console.selectInput().toLowerCase().strip();
    }

    public void doOutput(OutputConstant outputConstant) {
        console.outputResult(outputConstant.getOutputConstant());
    }
}
