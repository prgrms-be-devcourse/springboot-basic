package com.programmers.vouchermanagement.consoleapp.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consoleapp.menu.MenuHandler;

@Component
public class ConsoleAppRunner implements ApplicationRunner {
    private final MenuHandler menuHandler;

    public ConsoleAppRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean isRunning = true;
        while (isRunning) {
            isRunning = menuHandler.handleMenu();
        }
    }
}
