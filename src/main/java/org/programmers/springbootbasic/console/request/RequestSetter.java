package org.programmers.springbootbasic.console.request;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.console.ConsoleMapper;
import org.programmers.springbootbasic.console.Model;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.command.InputCommand.HELP;

@Component
@RequiredArgsConstructor
public class RequestSetter {
    //TODO GC 처리 지점 언제로 할 지

    private final Input input;

    public ConsoleRequest createInputRequestMessage(Model model) {
        Command command = setCommand();
        return new ConsoleRequest(model, command);
    }

    public ConsoleRequest createRedirectRequestMessage(Model model, RedirectCommand command) {
        return new ConsoleRequest(model, command);
    }
    private Command setCommand() {
        return ConsoleMapper.COMMANDS.getOrDefault(input.readLine(), HELP);
    }
}