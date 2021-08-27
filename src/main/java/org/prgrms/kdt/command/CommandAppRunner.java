package org.prgrms.kdt.command;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandAppRunner implements ApplicationRunner {

    private final CommandLineApplication commandLineApplication;

    public CommandAppRunner(CommandLineApplication commandLineApplication) {
        this.commandLineApplication = commandLineApplication;
    }

    @Override
    public void run(ApplicationArguments args) {
        commandLineApplication.run();
    }
}
