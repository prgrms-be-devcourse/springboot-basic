package com.programmers.voucher;

import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.menu.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final Console console;
    private final ConsoleMenu consoleMenu;

    public ConsoleRunner(Console console, ConsoleMenu consoleMenu) {
        this.console = console;
        this.consoleMenu = consoleMenu;
    }

    @Override
    public void run(String... args) {
        boolean keepRunningClient = true;
        while (keepRunningClient) {
            keepRunningClient = runAndProcessClient();
        }
    }

    private boolean runAndProcessClient() {
        boolean keepRunningClient = true;
        try {
            keepRunningClient = consoleMenu.runClient();

        } catch (IllegalArgumentException | NoSuchElementException | DuplicateKeyException ex) {
            console.print(ex.getMessage());

        } catch (RuntimeException ex) {
            console.print(ex.getMessage());

            keepRunningClient = false;
        }
        return keepRunningClient;
    }
}
