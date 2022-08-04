package org.programmers.springbootbasic.console.request;

import org.programmers.springbootbasic.console.model.ConsoleModel;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.command.InputCommand.HELP;

@Component
public class RequestCreator {

    public ConsoleRequest createInputRequestMessage(ConsoleModel consoleModel, String input) {
        var command = ConsoleMapper.COMMANDS.getOrDefault(input, HELP);
        return new ConsoleRequest(consoleModel, command);
    }

    public ConsoleRequest createRedirectRequestMessage(ConsoleModel consoleModel) {
        return new ConsoleRequest(consoleModel, consoleModel.getRedirectLink());
    }
}