package org.devcourse.voucher.controller.console;

import org.devcourse.voucher.controller.console.dto.Response;
import org.devcourse.voucher.controller.console.dto.Status;
import org.devcourse.voucher.controller.console.runner.CommandRunner;
import org.devcourse.voucher.view.Input;
import org.devcourse.voucher.view.Output;
import org.springframework.boot.CommandLineRunner;

import java.util.Map;

public class CommandDispatcher implements CommandLineRunner {

    private final Input input;
    private final Output output;
    private final Map<Command, CommandRunner> runners;

    public CommandDispatcher(Input input, Output output, Map<Command, CommandRunner> runners) {
        this.input = input;
        this.output = output;
        this.runners = runners;
    }

    @Override
    public void run(String... args) {
        Status status = Status.RUNNING;
        while (status.isRunning()) {
            String input = this.input.getUserInput();
            Command findCommand = Command.find(input);

            Response response = executeCommand(findCommand);
            status = response.status();
            String message = response.message();

            output.printMessage(message);
        }
    }

    private Response executeCommand(Command findCommand) {
        return runners.get(findCommand).run();
    }
}
