package com.wonu606.vouchermanager.console;

public abstract class AbstractConsoleIo {

    protected final ConsoleInput input;
    protected final ConsolePrinter printer;

    public AbstractConsoleIo(ConsoleInput input, ConsolePrinter printer) {
        this.input = input;
        this.printer = printer;
    }

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
