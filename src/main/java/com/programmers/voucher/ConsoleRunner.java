package com.programmers.voucher;

import com.programmers.voucher.global.io.menu.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final ConsoleMenu consoleMenu;

    public ConsoleRunner(ConsoleMenu consoleMenu) {
        this.consoleMenu = consoleMenu;
    }

    @Override
    public void run(String... args) {
        boolean keepRunningClient = true;
        while (keepRunningClient) {
            keepRunningClient = consoleMenu.runAndProcessClient();
        }
    }
}
