package com.programmers.vouchermanagement.consolecomponent;

import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleAppRunner implements ApplicationRunner {
    private final MenuHandler menuHandler;
    private final Logger logger;

    public ConsoleAppRunner(MenuHandler menuHandler, Logger logger) {
        this.menuHandler = menuHandler;
        this.logger = logger;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            menuHandler.handleMenu();
            run(args);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
    }
}
