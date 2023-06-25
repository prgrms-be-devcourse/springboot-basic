package com.programmers.voucher.view;

import com.programmers.voucher.constant.Style;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.Arrays;

public class ConsoleOutput implements Output {
    //TODO DI 적용?
    private static final TextIO textIO = TextIoFactory.getTextIO();
    private static final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    @Override
    public void displayCommands() {
        textTerminal.println("=== Voucher Program ===");

        Arrays.stream(Command.values())
                .forEach(command -> {
                    textTerminal.print("Type ");
                    textTerminal.print(Style.apply(command.getValue(), Style.BOLD));
                    textTerminal.println(" " + command.getText());
                });
    }
}
