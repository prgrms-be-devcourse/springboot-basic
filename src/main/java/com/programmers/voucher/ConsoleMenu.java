package com.programmers.voucher;

import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.request.VoucherCreateRequest;
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

    public ConsoleMenu(Console console, VoucherController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
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

    private boolean exitConsole() {
        LOG.info("Exit the console.");
        console.exit();

        return false;
    }
}
