package org.devcourse.voucher.controller.console;

import org.devcourse.voucher.controller.console.dto.Response;
import org.devcourse.voucher.controller.console.dto.Status;
import org.devcourse.voucher.view.Input;
import org.devcourse.voucher.view.Output;
import org.springframework.boot.CommandLineRunner;

public class CommandDispatcher implements CommandLineRunner {

    private final Input input;
    private final Output output;
    private final CommandRunnerRegistry runners;

    public CommandDispatcher(Input input, Output output, CommandRunnerRegistry runners) {
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

            Response response = runners.executeCommand(findCommand);
            String message = response.message();
            output.printMessage(message);

            status = response.status();
        }
    }

}
