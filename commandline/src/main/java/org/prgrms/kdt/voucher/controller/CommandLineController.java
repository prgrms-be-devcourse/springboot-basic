package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.io.CommandType;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.exception.*;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.service.VoucherService;
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
                    case FIND -> {
                        Long voucherId = console.inputVoucherId();
                        Voucher voucher = voucherService.findById(voucherId);
                        console.printVoucher(voucher);
                    }
                    case UPDATE -> {
                        Long voucherId = console.inputVoucherId();
                        Long discountDegree = console.inputDiscountDegree();
                        voucherService.updateVoucher(voucherId, discountDegree);
                        console.updateComplete();

                        Voucher voucher = voucherService.findById(voucherId);
                        console.printVoucher(voucher);
                    }
                    case DELETE -> {
                        voucherService.deleteAll();
                        console.delete();
                    }
                    default -> console.printError(ErrorCode.INPUT_EXCEPTION.getMessage());
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
                long discountValue = console.inputVoucherDiscountValue();

                voucherService.createVoucher(voucherType, discountValue);
            } catch (RuntimeException e) {
                console.printError(e.getMessage());
                logger.error(e.getMessage());
                continue;
            }
            continueJob = false;
        }
    }
}
