package com.programmers.vouchermanagement.consoleapp.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consoleapp.menu.MenuHandler;

@Component
public class ConsoleAppRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleAppRunner.class);

    private final MenuHandler menuHandler;

    public ConsoleAppRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
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
