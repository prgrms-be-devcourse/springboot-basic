package com.prgms.springbootbasic.command;

import com.prgms.springbootbasic.ui.Console;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements Command {

    private final Console console;

    public ExitCommand(Console console) {
        this.console = console;
    }

    @Override
    public void execute() {
        console.exit();
    }

}
