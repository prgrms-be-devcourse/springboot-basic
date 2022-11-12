package org.prgrms.kdt.controller;

import org.prgrms.kdt.CommandType;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.*;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.voucher.VoucherValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineController {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);

    private final Console console;
    private final VoucherService voucherService;

    public CommandLineController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            console.printCommandList();
            try {
                CommandType commandType = CommandType.selectType(console.inputCommand());
                switch (commandType) {
                    case CREAT -> {
                        createVoucher();
                    }
                    case LIST -> {
                        List<Voucher> vouchers = voucherService.getAllVoucher();
                        console.printVouchers(vouchers);
                    }
                    case EXIT -> {
                        console.terminate();
                        running = false;
                    }
                    default -> console.printError(ErrorCode.InputException.getMessage());
                }
            } catch (RuntimeException e) {
                console.printError(e.getMessage());
                logger.error(e.getMessage());
                continue;
            }
        }
    }

    private void createVoucher() {
        boolean continueJob = true;
        while (continueJob) {
            try {
                String voucherType = console.inputVoucherType();
                String discountValue = console.inputVoucherDiscountValue();
                VoucherValidator.validateVoucher(voucherType, discountValue);
                voucherService.createVoucher(voucherType, Long.parseLong(discountValue));
            } catch (RuntimeException e) {
                console.printError(e.getMessage());
                logger.error(e.getMessage());
                continue;
            }
            continueJob = false;
        }
    }
}
