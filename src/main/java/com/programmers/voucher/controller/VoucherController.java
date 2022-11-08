package com.programmers.voucher.controller;

import com.programmers.voucher.io.MenuType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.model.VoucherType;
import com.programmers.voucher.service.VoucherService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements ApplicationRunner {

    private Console console;
    private VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean isRunning = true;

        while (isRunning) {
            try {
                switch (getMenuType()) {
                    case EXIT:
                        isRunning = false;
                    case CREATE:
                        voucherService.create(getVoucherType(), getDiscountValue());
                        break;
                    case LIST:
                        console.printVouchers(voucherService.findAllVoucher());
                        break;
                }
            } catch (IllegalArgumentException e) {
                console.printError();
            }
        }
    }

    private MenuType getMenuType() {
        console.requestMenuType();
        String inputMenu = console.getInput();
        return MenuType.toMenuType(inputMenu);
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
