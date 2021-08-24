package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Output;
import org.springframework.stereotype.Component;

@Component
public class ServiceExit implements Command {

    private final Output output;

    public ServiceExit(Console console) {
        this.output = console;
    }


    @Override
    public boolean execute() {
        output.exit();
        return false;
    }
}
