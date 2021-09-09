package org.prgrms.kdt.app.command;

import org.prgrms.kdt.app.io.Console;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements CommandOperator {

    private final Console console;
    private final CommandType commandType;

    public ExitCommand(Console console) {
        this.console = console;
        this.commandType = CommandType.EXIT;
    }

    @Override
    public void execute() {
        console.printMessage("Bye!!");
        System.exit(0);
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
