package com.wonu606.vouchermanager.io;

import java.util.List;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput {

    private final TextIO textIO = TextIoFactory.getTextIO();

    public String readString(String description) {
        return textIO.newStringInputReader()
                .read(description);
    }

    public String readString(List<String> possibleValues, String description) {
        return textIO.newStringInputReader()
                .withInlinePossibleValues(possibleValues)
                .read(description);
    }

    public Double readDouble(String description) {
        return textIO.newDoubleInputReader()
                .read(description);
    }

    public void terminate() {
        textIO.dispose();
    }
}
