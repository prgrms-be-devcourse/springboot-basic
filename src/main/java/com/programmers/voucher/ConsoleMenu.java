package com.programmers.voucher;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.global.exception.DataAccessException;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.ConsoleCommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMenu implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleMenu.class);

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
            LOG.warn("Invalid input occurred.", ex);
            console.print(ex.getMessage());
        } catch (DataAccessException ex) {
            console.print(ex.getMessage());

            keepRunningClient = false;
        } catch (RuntimeException ex) {
            LOG.error("Unexpected exception thrown.", ex);
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
