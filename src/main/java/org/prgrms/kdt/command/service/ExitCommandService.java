package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Output;
import org.springframework.stereotype.Service;

@Service
public class ExitCommandService implements Command {
    private final Output output;

    public ExitCommandService(Output output) {
        this.output = output;
    }

    @Override
    public boolean execute() {
        output.printOnExit();
        return false;
    }
}
