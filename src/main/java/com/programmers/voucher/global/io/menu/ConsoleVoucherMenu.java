package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleVoucherMenu {
    private final Console console;
    private final VoucherController voucherController;

    public ConsoleVoucherMenu(Console console, VoucherController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
    }

    public void runningVoucherService() {
        console.printVoucherCommandSet();

        boolean run = true;
        while (run) {
            run = voucherMapping();
        }
    }

    private boolean voucherMapping() {
        VoucherCommandType voucherCommandType = console.inputVoucherCommandType();

        switch (voucherCommandType) {
            case CREATE -> {
                voucherController.createVoucher();
            }
            case LIST -> {
                voucherController.findVouchers();
            }
            case DELETE -> {
                voucherController.deleteVoucher();
            }
            case HELP -> {
                console.printVoucherCommandSet();
            }
            case EXIT -> {
                return false;
            }
        }
        return true;
    }
}
