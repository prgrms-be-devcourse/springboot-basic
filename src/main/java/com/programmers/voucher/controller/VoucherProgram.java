package com.programmers.voucher.controller;

import com.programmers.voucher.io.CommandType;
import com.programmers.voucher.io.View;
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
    private final View view;
    private final VoucherService voucherService;

    public VoucherProgram(View view, VoucherService voucherService) {
        this.view = view;
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
                        view.printVouchers(voucherService.findAllVoucher());
                        break;
                }
            } catch (IllegalArgumentException e) {
                logger.error("wrong order input");
                view.printError(e.getMessage());
            }
        }
    }

    private CommandType getCommandType() {
        view.requestMenuType();
        String command = view.getInput();
        return CommandType.toCommandType(command);
    }

    private VoucherType getVoucherType() {
        view.requestVoucherType();
        return VoucherType.toVoucherType(view.getInput());
    }

    private long getDiscountValue() {
        view.requestDiscountValue();
        return view.getInputDiscountValue();
    }
}
