package com.programmers.voucher;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.ConsoleCommandType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMenu implements CommandLineRunner {
    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public ConsoleMenu(Console console, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run(String... args) throws Exception {
        console.printCommandSet();

        boolean keepRunningClient = true;
        while (keepRunningClient) {
            keepRunningClient = runAndProcessClient();
        }
    }

    private boolean runAndProcessClient() {
        boolean keepRunningClient = true;
        try {
            keepRunningClient = runClient();

        } catch (IllegalArgumentException ex) {
            console.print(ex.getMessage());

        }  catch (RuntimeException ex) {
            console.print(ex.getMessage());

            keepRunningClient = false;
        }
        return keepRunningClient;
    }

    public boolean runClient() {
        ConsoleCommandType commandType = console.inputInitialCommand();

        switch (commandType) {
            case CREATE -> {
                voucherController.createVoucher();
            }
            case LIST -> {
                voucherController.findVouchers();
            }
            case HELP -> {
                console.printCommandSet();
            }
            case BLACKLIST -> {
                customerController.findBlacklistCustomers();
            }
            case EXIT -> {
                console.exit();

                return false;
            }
        }

        return true;
    }
}
