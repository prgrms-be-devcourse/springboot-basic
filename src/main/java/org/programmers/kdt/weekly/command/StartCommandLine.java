package org.programmers.kdt.weekly.command;

import java.util.List;
import org.programmers.kdt.weekly.command.io.Console;
import org.programmers.kdt.weekly.command.io.ErrorType;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StartCommandLine {

    private static final Logger logger = LoggerFactory.getLogger(StartCommandLine.class);

    private final VoucherWalletCommandLine voucherWalletCommandLine;
    private final VoucherService voucherService;
    private final Console console;
    private final CustomerCommandLine customerCommand;

    public StartCommandLine(
        VoucherWalletCommandLine voucherWalletCommandLine,
        VoucherService voucherService,
        Console console,
        CustomerCommandLine customerCommandLine) {
        this.voucherWalletCommandLine = voucherWalletCommandLine;
        this.voucherService = voucherService;
        this.console = console;
        this.customerCommand = customerCommandLine;
    }

    public void run() {
        StartCommandType startCommandType = StartCommandType.DEFAULT;

        while (startCommandType.isRunnable()) {
            this.console.printVoucherMessage();
            var userInput = this.console.getUserInput();

            try {
                startCommandType = StartCommandType.findByCommand(userInput);
            } catch (IllegalArgumentException e) {
                logger.debug("잘못된 사용자 입력 -> {}", userInput);
                this.console.printErrorMessage(ErrorType.COMMAND);
            }

            switch (startCommandType) {
                case VOUCHER_CREATE -> this.createVoucher();
                case VOUCHER_LIST -> this.showVoucherList();
                case CUSTOMER -> this.customerCommand.run();
                case WALLET -> this.voucherWalletCommandLine.run();
                case EXIT -> this.console.programExitMessage();
                default -> this.console.printErrorMessage(ErrorType.COMMAND);
            }
        }
    }

    private void createVoucher() {
        this.console.printVoucherSelectMessage();
        try {
            var selectVoucherType = Integer.parseInt(console.getUserInput());
            var voucherType = VoucherType.findByNumber(selectVoucherType);
            this.console.printVoucherDiscountSelectMessage();
            var voucherValue = Integer.parseInt(this.console.getUserInput());
            voucherService.create(voucherType, voucherValue);
            this.console.printExecutionSuccessMessage();
        } catch (IllegalArgumentException e) {
            logger.error("create voucher user input error -> {}", e);
            this.console.printErrorMessage(ErrorType.INVALID);
        }
    }

    private void showVoucherList() {
        List<Voucher> vouchers = voucherService.getVouchers();

        if (vouchers.size() > 0) {
            vouchers.forEach((v) -> System.out.println(v.toString()));
            return;
        }
        this.console.printErrorMessage(ErrorType.VOUCHER_EMPTY);
    }
}