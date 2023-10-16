package com.programmers.vouchermanagement;

import org.beryx.textio.TextIO;
import org.springframework.stereotype.Component;

@Component
public class ConsoleManager {
    private static final String menuSelectionInstruction = """
            ===Voucher Program ===
            Type **exit** to exit the program.
            Type **create** to create a new voucher.
            Type **list** to list all vouchers.
            """;
    private final TextIO textIO;

    public ConsoleManager(TextIO textIO) {
        this.textIO = textIO;
    }

    public String selectMenu() {
        return textIO.newStringInputReader()
                .read(menuSelectionInstruction);
    }

    public void printExit() {
        textIO.getTextTerminal().println("System exits.");
    }
}
