package com.programmers.voucher.io;

import com.programmers.voucher.enumtype.ConsoleCommandType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

@Component
public class TextIoConsole implements Console {
    private final TextIO textIO;

    public TextIoConsole() {
        this.textIO = TextIoFactory.getTextIO();
    }

    @Override
    public ConsoleCommandType inputInitialCommand() {
        String command = textIO.newStringInputReader()
                .read();

        return ConsoleCommandType.getValue(command);
    }

    @Override
    public void printCommandSet() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.println("=== Voucher Program ===");

        textTerminal.print("Type");
        textTerminal.executeWithPropertiesConfigurator(
                props -> props.setPromptBold(true),
                t -> t.print(" exit ")
        );
        textTerminal.println("to exit the program.");

        textTerminal.print("Type");
        textTerminal.executeWithPropertiesConfigurator(
                props -> props.setPromptBold(true),
                t -> t.print(" create ")
        );
        textTerminal.println("to create a new voucher.");

        textTerminal.print("Type");
        textTerminal.executeWithPropertiesConfigurator(
                props -> props.setPromptBold(true),
                t -> t.print(" list ")
        );
        textTerminal.println("to list all vouchers.");

        textTerminal.print("Type");
        textTerminal.executeWithPropertiesConfigurator(
                props -> props.setPromptBold(true),
                t -> t.print(" help ")
        );
        textTerminal.println("to list command set.");

    }

    @Override
    public String input(String hint) {
        return textIO.newStringInputReader()
                .read(hint);
    }

    @Override
    public Integer intInput(String hint) {
        return textIO.newIntInputReader()
                .read(hint);
    }

    @Override
    public void print(String result) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println(result);
    }

    @Override
    public void exit() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println("Bye Bye.");
    }

}
