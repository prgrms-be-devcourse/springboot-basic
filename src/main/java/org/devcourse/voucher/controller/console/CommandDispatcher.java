package org.devcourse.voucher.controller.console;

import org.devcourse.voucher.controller.console.runner.CommandRunner;
import org.devcourse.voucher.view.Input;
import org.devcourse.voucher.view.Output;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Map;

public class CommandDispatcher implements CommandLineRunner {

    private final Input input;
    private final Output output;
    private final List<CommandRunner> runners;

    public CommandDispatcher(Input input, Output output, List<CommandRunner> runners) {
        this.input = input;
        this.output = output;
        this.runners = runners;
    }

    @Override
    public void run(String... args) throws Exception {
        String commandName = input.getUserInput();
        Command findCommand = Command.find(commandName);

        String message = executeCommand(findCommand);

        output.printMessage(message);
    }

    private String executeCommand(Command findCommand) {
        for (CommandRunner runner : runners) {
            if (runner.isSupport(findCommand)) {
                return runner.run();
            }
        }

        throw new RuntimeException("명령어를 실행 가능한 러너가 없습니다");
    }
}
