package com.programmers.voucher.global.io;

import com.programmers.voucher.global.util.ConsoleMessages;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import static com.programmers.voucher.global.util.ConsoleMessages.*;

@Component
public class TextIoOutput implements ConsoleOutput {
    private final TextIO textIO;

    public TextIoOutput(TextIO textIO) {
        this.textIO = textIO;
    }

    @Override
    public void printCommandSet() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.println(ConsoleMessages.VOUCHER_PROGRAM);
        printCommand(ConsoleCommandType.EXIT, EXIT_BEHAVIOR);
        printCommand(ConsoleCommandType.CREATE, CREATE_BEHAVIOR);
        printCommand(ConsoleCommandType.LIST, LIST_BEHAVIOR);
        printCommand(ConsoleCommandType.HELP, HELP_BEHAVIOR);
        printCommand(ConsoleCommandType.BLACKLIST, BLACKLIST_BEHAVIOR);
    }

    private void printCommand(ConsoleCommandType commandType, String behavior) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.print(INPUT);
        textTerminal.executeWithPropertiesConfigurator(
                props -> props.setPromptBold(true),
                t -> t.print(" " + commandType.getInput() + " ")
        );
        textTerminal.println(behavior);
    }

    @Override
    public void print(String result) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println(result);
    }

    @Override
    public void exit() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println(EXIT_CONSOLE);
    }
}
