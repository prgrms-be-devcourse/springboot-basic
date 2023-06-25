package com.programmers.voucher;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.enumtype.VoucherType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConsoleMenu implements CommandLineRunner {
    private final static Logger log = LoggerFactory.getLogger(ConsoleMenu.class);

    private final Console console;
    private final VoucherService voucherService;

    public ConsoleMenu(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

        log.info("Started Voucher Console Application.");
    @Override
    public void run(String... args) throws Exception {
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
                log.info("Create voucher.");
                String rawVoucherType = console.input("1. [fixed | percent]");
                VoucherType voucherType = VoucherType.getValue(rawVoucherType);

                Integer amount = console.intInput("2. [amount]");
                voucherType.validateAmount(amount);

                VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(voucherType, amount);
                UUID voucherId = voucherService.createVoucher(voucherCreateRequest);

                console.print("Created new voucher. VoucherID: " + voucherId.toString());
                log.info("End create voucher.");
            }
            case LIST -> {
                log.info("Lists the vouchers.");
                List<Voucher> vouchers = voucherService.findVouchers();

                String vouchersForPrint = vouchers.stream()
                        .map(Voucher::fullInfoString)
                        .reduce("", (a, b) -> a + "\n" + b);

                console.print(vouchersForPrint);
                log.info("End listing the vouchers.");
            }
            case HELP -> {
                log.info("Prints the help commands.");
                console.printCommandSet();
            }
            case EXIT -> {
                log.info("Exit the console.");
                console.exit();

                return false;
            }
        }

        return true;
    }
}
