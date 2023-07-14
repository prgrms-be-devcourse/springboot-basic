package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ConsoleMenu {
    private final Console console;
    private final ConsoleCustomerMenu consoleCustomerMenu;
    private final ConsoleVoucherMenu consoleVoucherMenu;

    public ConsoleMenu(Console console, ConsoleCustomerMenu consoleCustomerMenu, ConsoleVoucherMenu consoleVoucherMenu) {
        this.console = console;
        this.consoleCustomerMenu = consoleCustomerMenu;
        this.consoleVoucherMenu = consoleVoucherMenu;
    }

    public boolean runAndProcessClient() {
        boolean keepRunningClient = true;
        try {
            keepRunningClient = runClient();

        } catch (IllegalArgumentException | NoSuchElementException | DuplicateKeyException ex) {
            console.printErrorMessage(ex);
        } catch (RuntimeException ex) {
            console.printErrorMessage(ex);
            keepRunningClient = false;
        }
        return keepRunningClient;
    }

    private boolean runClient() {
        console.printCommandSet();

        ConsoleCommandType commandType = console.inputInitialCommand();
        switch (commandType) {
            case CUSTOMER -> {
                consoleCustomerMenu.runningCustomerService();
            }
            case VOUCHER -> {
                consoleVoucherMenu.runningVoucherService();
            }
            case HELP -> {
                return true;
            }
            case EXIT -> {
                console.exit();

                return false;
            }
        }

        return true;
    }
}
