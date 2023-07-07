package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import org.springframework.stereotype.Component;

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

    public boolean runClient() {
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
