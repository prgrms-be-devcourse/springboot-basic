package com.programmers.vouchermanagement;

import org.springframework.stereotype.Component;

@Component
public class Client {
    private final ConsoleManager consoleManager;

    public Client(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    public boolean selectMenu() {
        String menu = consoleManager.selectMenu();

        if (!validateMenu(menu)) {
            consoleManager.printExit();
            return false;
        }

        return true;
    }

    private boolean validateMenu(String menu) {
        if (menu.equals("exit")) {
            return false;
        }

        return true;
    }
}
