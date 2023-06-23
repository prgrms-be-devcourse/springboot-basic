package com.programmers.voucher.io;

import com.programmers.voucher.controller.VoucherConsoleController;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.enumtype.VoucherType;
import com.programmers.voucher.request.VoucherCreateRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConsoleMenu {
    private final Console console;
    private final VoucherConsoleController consoleClient;

    public ConsoleMenu(Console console, VoucherConsoleController consoleClient) {
        this.console = console;
        this.consoleClient = consoleClient;
    }

    public void start() {
        console.printCommandSet();

        boolean keepRunningClient = true;
        while(keepRunningClient) {
            keepRunningClient = runAndProcessClient();
        }
    }

    private boolean runAndProcessClient() {
        boolean keepRunningClient = true;
        try {
            keepRunningClient = runClient();
        } catch (RuntimeException ex) {
            console.print(ex.getMessage());
        }
        return keepRunningClient;
    }

    public boolean runClient() {
        ConsoleCommandType commandType = console.inputInitialCommand();

        switch (commandType) {
            case CREATE -> {
                String rawVoucherType = console.input("1. [fixed | percent]");
                VoucherType voucherType = VoucherType.getValue(rawVoucherType);

                Integer amount = console.intInput("2. [amount]");
                voucherType.validateAmount(amount);

                VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(voucherType, amount);
                UUID voucherId = consoleClient.createVoucher(voucherCreateRequest);

                console.print("Created new voucher. VoucherID: " + voucherId.toString());
            }
            case LIST -> {
                List<Voucher> vouchers = consoleClient.findVouchers();

                String vouchersForPrint = vouchers.stream()
                        .map(Voucher::fullInfoString)
                        .reduce("", (a, b) -> a + "\n" + b);

                console.print(vouchersForPrint);
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
}
