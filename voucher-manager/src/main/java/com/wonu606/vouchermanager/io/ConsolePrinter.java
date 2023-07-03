package com.wonu606.vouchermanager.io;

import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

@Component
public class ConsolePrinter {

    private final TextTerminal<?> textTerminal = TextIoFactory.getTextTerminal();

    public void displayMessage(String message) {
        textTerminal.println(message);
    }

    public void terminate() {
        textTerminal.dispose();
    }
}
