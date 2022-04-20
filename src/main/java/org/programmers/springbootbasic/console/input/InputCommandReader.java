package org.programmers.springbootbasic.console.input;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.console.ConsoleMappingData;
import org.programmers.springbootbasic.console.ConsoleRequest;
import org.programmers.springbootbasic.console.Model;
import org.programmers.springbootbasic.console.command.Command;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.command.InputCommand.HELP;

@Component
@RequiredArgsConstructor
public class InputCommandReader {
    //TODO GC 처리 지점 언제로 할 지

    private final Input input;

    public ConsoleRequest assembleRequestMessage(Model model) {
        Command command = setCommand();
        return new ConsoleRequest(model, command);
    }

    private Command setCommand() {
        return ConsoleMappingData.COMMANDS.getOrDefault(input.readLine(), HELP);
    }
}