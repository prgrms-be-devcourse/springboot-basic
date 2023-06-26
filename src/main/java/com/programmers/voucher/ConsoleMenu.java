package com.programmers.voucher;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConsoleMenu implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ConsoleMenu.class);

    private final Console console;
    private final VoucherService voucherService;

    public ConsoleMenu(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Started Voucher Console Application.");
        console.printCommandSet();

        boolean keepRunningClient = true;
        while (keepRunningClient) {
            keepRunningClient = runAndProcessClient();
        }

        log.info("Exit the Voucher Console Application.");
    }

    private boolean runAndProcessClient() {
        boolean keepRunningClient = true;
        try {
            keepRunningClient = runClient();
        } catch (RuntimeException ex) {
            log.warn("Invalid input occurred.", ex);
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
        log.info("Create voucher.");
        VoucherCreateRequest voucherCreateRequest = console.inputVoucherCreateInfo();
        UUID voucherId = voucherService.createVoucher(voucherCreateRequest);

        console.print("Created new voucher. VoucherID: " + voucherId.toString());
        log.info("End create voucher.");
    }

    private void listVouchers() {
        log.info("Lists the vouchers.");
        List<Voucher> vouchers = voucherService.findVouchers();

        String vouchersForPrint = vouchers.stream()
                .map(Voucher::fullInfoString)
                .reduce("", (a, b) -> a + "\n" + b);

        console.print(vouchersForPrint);
        log.info("End listing the vouchers.");
    }

    private void printCommandSet() {
        log.info("Prints the help commands.");
        console.printCommandSet();
    }

    private boolean exitConsole() {
        log.info("Exit the console.");
        console.exit();

        return false;
    }
}
