package com.programmers.vouchermanagement.consolecomponent;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consolecomponent.MenuHandler;

@Component
public class ConsoleAppRunner implements ApplicationRunner {
    private final MenuHandler menuHandler;

    public ConsoleAppRunner(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            menuHandler.handleMenu();
            run(args);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
