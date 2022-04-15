package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class CommandLineRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

    private static final String EXIT = "exit";
    private static final String CREATE = "create";
    private static final String LIST = "list";

    private final ApplicationContext applicationContext;
    private final Input input;
    private final Output output;

    public CommandLineRunner(ApplicationContext applicationContext, Input input, Output output) {
        this.applicationContext = applicationContext;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            output.printCommandManual();

            String command = input.input();
            switch (command) {
                case EXIT:
                    isRunning = false;
                    output.printShutDownSystem();
                    break;
                case CREATE:
                    logger.info("execute create command");
                    break;
                case LIST:
                    logger.info("execute list command");
                    break;
                default:
                    output.printInvalidCommand();
                    break;
            }
        }

    }
}
