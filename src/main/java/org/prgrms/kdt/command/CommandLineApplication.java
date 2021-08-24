package org.prgrms.kdt.command;

import org.prgrms.kdt.command.controller.CommandController;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements Runnable {

    private static final String INPUT_PROMPT = "> ";

    private final Input input;
    private final Output output;
    private final CommandController commandController;

    public CommandLineApplication(Console console, CommandController commandController) {
        this.input = console;
        this.output = console;
        this.commandController = commandController;
    }

    @Override
    public void run() {
        output.init(); // command 설명
        while (excute()) ; // 실행
    }


    private boolean excute() {
        String inputCommandType = input.inputString(INPUT_PROMPT);
        return commandController.getCommandService(inputCommandType).execute();
    }
}
