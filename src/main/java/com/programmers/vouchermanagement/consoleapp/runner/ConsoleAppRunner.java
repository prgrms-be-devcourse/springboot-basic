package com.programmers.vouchermanagement.consoleapp.runner;

import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consoleapp.menu.MenuHandler;

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
        boolean isRunning = true;
        while (isRunning) {
            try {
                isRunning = menuHandler.handleMenu();
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
