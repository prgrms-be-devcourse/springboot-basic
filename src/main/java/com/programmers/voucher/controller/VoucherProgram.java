package com.programmers.voucher.controller;

import com.programmers.voucher.io.CommandType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherProgram implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Console console;
    private final VoucherService voucherService;

    public VoucherProgram(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean isRunning = true;

        while (isRunning) {
            try {
                switch (getCommandType()) {
                    case EXIT:
                        isRunning = false;
                        break;
                    case CREATE:
                        voucherService.create(getVoucherType(), getDiscountValue());
                        break;
                    case LIST:
                        console.printVouchers(voucherService.findAllVoucher());
                        break;
                }
            } catch (IllegalArgumentException e) {
                logger.error("wrong order input");
                console.printError(e.getMessage());
            }
        }
    }

    private CommandType getCommandType() {
        console.requestMenuType();
        String command = console.getInput();
        return CommandType.toCommandType(command);
    }

    private VoucherType getVoucherType() {
        console.requestVoucherType();
        return VoucherType.toVoucherType(console.getInput());
    }

    private long getDiscountValue() {
        console.requestDiscountValue();
        return console.getInputDiscountValue();
    }
}
