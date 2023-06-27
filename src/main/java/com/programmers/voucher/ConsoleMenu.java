package com.programmers.voucher;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.global.exception.DataAccessException;
import com.programmers.voucher.global.io.ConsoleCommandType;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
        LOG.info("Started Voucher Console Application.");
        console.printCommandSet();

        boolean keepRunningClient = true;
        while (keepRunningClient) {
            keepRunningClient = runAndProcessClient();
        }

        LOG.info("Exit the Voucher Console Application.");
    }

    private boolean runAndProcessClient() {
        boolean keepRunningClient = true;
        try {
            keepRunningClient = runClient();
        } catch (DataAccessException ex) {
            LOG.error("Unhandled exception thrown: " + CommonErrorMessages.CANNOT_ACCESS_FILE, ex);
            console.print(ex.getMessage());

            keepRunningClient = false;
        } catch (RuntimeException ex) {
            LOG.warn("Invalid input occurred.", ex);
            console.print(ex.getMessage());
        }
        return keepRunningClient;
    }

    public boolean runClient() {
        ConsoleCommandType commandType = console.inputInitialCommand();

        switch (commandType) {
            case CREATE -> {
                createVoucher();
            }
            case LIST -> {
                listVouchers();
            }
            case HELP -> {
                printCommandSet();
            }
            case BLACKLIST -> {
                printBlacklist();
            }
            case EXIT -> {
                return exitConsole();
            }
        }

        return true;
    }

    private void createVoucher() {
        LOG.info("Create voucher.");
        VoucherCreateRequest voucherCreateRequest = console.inputVoucherCreateInfo();
        UUID voucherId = voucherController.createVoucher(voucherCreateRequest);

        console.print("Created new voucher. VoucherID: " + voucherId.toString());
        LOG.info("End create voucher.");
    }

    private void listVouchers() {
        LOG.info("Lists the vouchers.");
        List<Voucher> vouchers = voucherController.findVouchers();

        String vouchersForPrint = vouchers.stream()
                .map(Voucher::fullInfoString)
                .reduce("", (a, b) -> a + "\n" + b);

        console.print(vouchersForPrint);
        LOG.info("End listing the vouchers.");
    }

    private void printCommandSet() {
        LOG.info("Prints the help commands.");
        console.printCommandSet();
    }

    private void printBlacklist() {
        List<Customer> customers = customerController.findBlacklistCustomers();
        String customerInfos = customers.stream()
                .map(Customer::fullInfoString)
                .reduce("", (a, b) -> a + "\n" + b);

        console.print(customerInfos);
    }

    private boolean exitConsole() {
        LOG.info("Exit the console.");
        console.exit();

        return false;
    }
}
