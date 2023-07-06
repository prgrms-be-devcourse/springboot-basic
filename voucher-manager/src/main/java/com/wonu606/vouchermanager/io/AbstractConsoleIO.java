package com.wonu606.vouchermanager.io;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractConsoleIO {

    protected final ConsoleInput input;
    protected final ConsolePrinter printer;

    public String readString(String description) {
        return input.readString(description);
    }

    public double readDouble(String description) {
        return input.readDouble(description);
    }

    public void displayMessage(String message) {
        printer.displayMessage(message);
    }

    public void terminal() {
        input.terminate();
        printer.terminate();
    }

    public abstract Object selectMenu();
}
