package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExitCommandService implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ExitCommandService.class);
    private final Output output;

    public ExitCommandService(Output output) {
        this.output = output;
    }

    @Override
    public boolean execute() {
        logger.info("Execute exit CLI");
        output.printOnExit();
        return false;
    }
}
