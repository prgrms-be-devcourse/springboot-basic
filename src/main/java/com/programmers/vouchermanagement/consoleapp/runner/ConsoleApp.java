package com.programmers.vouchermanagement.consoleapp.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.configuration.profiles.ConsoleCondition;
import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.consoleapp.menu.Menu;
import com.programmers.vouchermanagement.consoleapp.menu.MenuHandler;

@Component
@Conditional(ConsoleCondition.class)
public class ConsoleApp implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleApp.class);
    private static final String INCORRECT_MESSAGE = "Selected menu is not an executable menu.";

    private final MenuHandler menuHandler;
    private final ConsoleManager consoleManager;

    public ConsoleApp(MenuHandler menuHandler, ConsoleManager consoleManager) {
        this.menuHandler = menuHandler;
        this.consoleManager = consoleManager;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean isRunning = true;
        while (isRunning) {
            Menu menu = consoleManager.selectMenu();
            menuHandler.handleMenu(menu);
            isRunning = isValidMenu(menu);
        }
    }

    private boolean isValidMenu(Menu menu) {
        if (menu.isExit()) {
            return false;
        }

        if (menu.isIncorrect()) {
            logger.error(INCORRECT_MESSAGE);
        }

        return true;
    }
}
