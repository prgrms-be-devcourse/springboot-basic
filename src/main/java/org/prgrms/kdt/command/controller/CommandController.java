package org.prgrms.kdt.command.controller;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.domain.CommandType;
import org.prgrms.kdt.command.service.CreateCommandService;
import org.prgrms.kdt.command.service.ExitCommandService;
import org.prgrms.kdt.command.service.ListCommandService;
import org.springframework.stereotype.Component;

@Component
public class CommandController {
    private final CreateCommandService createCommand;
    private final ListCommandService listCommand;
    private final ExitCommandService exitCommand;

    public CommandController(CreateCommandService createCommand, ListCommandService listCommand, ExitCommandService exitCommand) {
        this.createCommand = createCommand;
        this.listCommand = listCommand;
        this.exitCommand = exitCommand;
    }

    public Command getCommandService(String inputString) {
        CommandType type = parseInput(inputString);
        switch (type) {
            case CREATE:
                return createCommand;
            case LIST:
                return listCommand;
            case EXIT:
                return exitCommand;
            default:
                throw new IllegalArgumentException();
        }
    }

    private CommandType parseInput(String inputString) {
        return CommandType.findCommand(inputString);
    }
}
