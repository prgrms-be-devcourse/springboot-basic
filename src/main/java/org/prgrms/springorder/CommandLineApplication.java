package org.prgrms.springorder;

import org.prgrms.springorder.console.core.Command;
import org.prgrms.springorder.console.core.CommandLineDispatcher;
import org.prgrms.springorder.console.io.Request;
import org.prgrms.springorder.console.io.Console;
import org.prgrms.springorder.console.io.ConsoleRequestBuilder;
import org.prgrms.springorder.console.core.ConsoleRunningStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements CommandLineRunner {

    private final CommandLineDispatcher dispatcher;

    private final ConsoleRequestBuilder consoleRequestBuilder;

    private final Console console;

    private static final Logger logger = LoggerFactory.getLogger(CommandLineDispatcher.class);

    public CommandLineApplication(CommandLineDispatcher dispatcher,
        ConsoleRequestBuilder consoleRequestBuilder, Console console) {
        this.dispatcher = dispatcher;
        this.consoleRequestBuilder = consoleRequestBuilder;
        this.console = console;
    }

    @Override
    public void run(String... args) {
        while (ConsoleRunningStatus.isRunning()) {
            try {
                console.displayCommandGuide();

                String inputString = console.input();

                Command command = Command.of(inputString);

                Request request = consoleRequestBuilder.request(command);

                console.showMessage(dispatcher.request(command, request).getResponse());

            } catch (RuntimeException e) {
                logger.warn("errorName : {}, errorMessage : {}", e.getClass().getName(),
                    e.getMessage());
                console.showMessage(e.getMessage());
            }
        }
    }
}
