package org.programmers.springbootbasic.console.request;

import org.programmers.springbootbasic.console.model.Model;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.command.InputCommand.HELP;

@Component
public class RequestCreator {

    public ConsoleRequest createInputRequestMessage(Model model, String input) {
        var command = ConsoleMapper.COMMANDS.getOrDefault(input, HELP);
        return new ConsoleRequest(model, command);
    }

    public ConsoleRequest createRedirectRequestMessage(Model model) {
        return new ConsoleRequest(model, model.getRedirectLink());
    }
}