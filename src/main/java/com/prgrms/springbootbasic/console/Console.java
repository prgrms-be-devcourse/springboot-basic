package com.prgrms.springbootbasic.console;

import com.prgrms.springbootbasic.handler.menu.dto.MenuInputResult;
import org.springframework.stereotype.Component;

@Component
public class Console {

    private final Reader reader;
    private final Printer printer;

    public Console(Reader reader, Printer printer) {
        this.reader = reader;
        this.printer = printer;
    }

    public MenuInputResult getCommand() {
        printer.printMenu();
        return new MenuInputResult(reader.read());
    }

    public void printInvalidMessage(String message) {
        printer.printInvalidMessage(message);
    }

    public void printExitMessage() {
        printer.printExitMessage();
    }
}
