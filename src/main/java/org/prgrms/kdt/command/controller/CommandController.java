package org.prgrms.kdt.command.controller;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.domain.CommandType;
import org.prgrms.kdt.command.service.BlackListCommandService;
import org.prgrms.kdt.command.service.CreateCommandService;
import org.prgrms.kdt.command.service.ExitCommandService;
import org.prgrms.kdt.command.service.ListCommandService;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class CommandController {
    private final CreateCommandService createCommand;
    private final ListCommandService listCommand;
    private final ExitCommandService exitCommand;
    private final BlackListCommandService blacklistCommand;

    public CommandController(
            CreateCommandService createCommand,
            ListCommandService listCommand,
            ExitCommandService exitCommand,
            BlackListCommandService blacklistCommand
    ) {
        this.createCommand = createCommand;
        this.listCommand = listCommand;
        this.exitCommand = exitCommand;
        this.blacklistCommand = blacklistCommand;
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
            case BLACKLIST:
                return blacklistCommand;
            default:
                throw new IllegalArgumentException(MessageFormat.format("알수 없는 명령어 입니다. : {}", inputString));
        }
    }

    private CommandType parseInput(String inputString) {
        return CommandType.findCommand(inputString);
    }
}
