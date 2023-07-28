package org.devcourse.voucher.controller.console;

import org.devcourse.voucher.controller.console.dto.Response;
import org.devcourse.voucher.controller.console.dto.Status;
import org.devcourse.voucher.controller.console.runner.CommandRunner;

import java.util.Map;

import static org.devcourse.voucher.controller.console.Command.*;

public class CommandRunnerRegistry {

    private final Map<Command, CommandRunner> runners;

    public CommandRunnerRegistry(CommandRunner createCommandRunner, CommandRunner listCommandRunner, CommandRunner exitCommandRunner) {
        this.runners = Map.of(
                CREATE, createCommandRunner,
                LIST, listCommandRunner,
                EXIT, exitCommandRunner,
                RETRY, () -> new Response(Status.RUNNING, "")
        );
    }

    public Response executeCommand(Command command) {
        return runners.get(command).run();
    }
}
