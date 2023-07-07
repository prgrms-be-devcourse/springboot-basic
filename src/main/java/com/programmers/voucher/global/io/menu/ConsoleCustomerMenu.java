package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleCustomerMenu {
    private final Console console;
    private final CustomerController customerController;

    public ConsoleCustomerMenu(Console console, CustomerController customerController) {
        this.console = console;
        this.customerController = customerController;
    }

    public void runningCustomerService() {
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
