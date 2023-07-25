package com.wonu606.vouchermanager.io;

import com.wonu606.vouchermanager.menu.VoucherMenu;

public abstract class AbstractConsoleIO {

    protected final ConsoleInput input;
    protected final ConsolePrinter printer;

    public AbstractConsoleIO(ConsoleInput input, ConsolePrinter printer) {
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
