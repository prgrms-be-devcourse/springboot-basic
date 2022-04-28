package org.programmers.kdt.weekly.command;

import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.command.io.Console;
import org.programmers.kdt.weekly.command.io.ErrorType;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherCommandLine {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCommandLine.class);

    private final Console console;
    private final VoucherService voucherService;

    public VoucherCommandLine(Console console,
        VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {
        VoucherCommandType voucherCommandType = VoucherCommandType.DEFAULT;

        while (voucherCommandType.isRunnable()) {
            this.console.printVoucherCommand();
            var userInput = this.console.getUserInput();

            try {
                voucherCommandType = VoucherCommandType.of(userInput);
            } catch (IllegalArgumentException e) {
                logger.debug("잘못된 사용자 입력 -> {}", userInput);
                this.console.printErrorMessage(ErrorType.COMMAND);
            }

            switch (voucherCommandType) {
                case VOUCHER_CREATE -> this.createVoucher();
                case VOUCHER_LIST -> this.showVoucherList();
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
            this.voucherService.create(UUID.randomUUID(), voucherType, voucherValue);
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
