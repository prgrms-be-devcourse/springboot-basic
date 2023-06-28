package com.wonu606.vouchermanager.io;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInputView {

    private final TextIO textIO;

    public ConsoleInputView() {
        this.textIO = TextIoFactory.getTextIO();
    }

    public String inputString(String description) {
        return textIO.newStringInputReader()
                .read(description);
    }

    public Double inputDouble(String description) {
        return textIO.newDoubleInputReader()
                .read(description);
    }

    public void close() {
        textIO.dispose();
    }
}
