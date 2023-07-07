package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMenu {
    private final Console console;
    private final CustomerController customerController;
    private final ConsoleVoucherMenu consoleVoucherMenu;

    public ConsoleMenu(Console console, CustomerController customerController, ConsoleVoucherMenu consoleVoucherMenu) {
        this.console = console;
        this.customerController = customerController;
        this.consoleVoucherMenu = consoleVoucherMenu;
    }

    public boolean runClient() {
        console.printCommandSet();

        ConsoleCommandType commandType = console.inputInitialCommand();
        switch (commandType) {
            case CUSTOMER -> {
                runningCustomerService();
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

    private void runningCustomerService() {
        console.printCustomerCommandSet();

        boolean run = true;
        while (run) {
            run = customerMapping();
        }
    }

    private boolean customerMapping() {
        CustomerCommandType customerCommandType = console.inputCustomerCommandType();

        switch (customerCommandType) {
            case CREATE -> {
                customerController.createCustomer();
            }
            case LIST -> {
                customerController.findCustomers();
            }
            case UPDATE -> {
                customerController.updateCustomer();
            }
            case DELETE -> {
                customerController.deleteCustomer();
            }
            case BLACKLIST -> {
                customerController.findBlacklistCustomers();
            }
            case HELP -> {
                console.printCustomerCommandSet();
            }
            case EXIT -> {
                return false;
            }
        }
        return true;
    }
}
