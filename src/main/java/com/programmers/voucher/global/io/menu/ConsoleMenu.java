package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMenu {
    private final Console console;
    private final CustomerController customerController;
    private final VoucherController voucherController;

    public ConsoleMenu(Console console, CustomerController customerController, VoucherController voucherController) {
        this.console = console;
        this.customerController = customerController;
        this.voucherController = voucherController;
    }

    public boolean runClient() {
        ConsoleCommandType commandType = console.inputInitialCommand();

        switch (commandType) {
            case CUSTOMER -> {
                customerMapping();
            }
            case VOUCHER -> {
                voucherMapping();
            }
            case HELP -> {
                console.printCommandSet();
            }
            case EXIT -> {
                console.exit();

                return false;
            }
        }

        return true;
    }

    public void voucherMapping() {
        VoucherCommandType voucherCommandType = console.inputVoucherCommandType();

        switch (voucherCommandType) {
            case CREATE -> {
                voucherController.createVoucher();
            }
            case LIST -> {
                voucherController.findVouchers();
            }
        }
    }

    public void customerMapping() {
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
        }
    }
}
