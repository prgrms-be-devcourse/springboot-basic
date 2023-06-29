package com.demo.voucher.controller;

import com.demo.voucher.domain.VoucherType;
import com.demo.voucher.io.CommandType;
import com.demo.voucher.io.ConsoleView;
import com.demo.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements Runnable {
    private final ConsoleView consoleView;
    private final VoucherService voucherService;

    public VoucherController(ConsoleView consoleView, VoucherService voucherService) {
        this.consoleView = consoleView;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        consoleView.showMenu();

        boolean isProgramRunnable = true;
        while (isProgramRunnable) {
            String inputCommand = consoleView.requestMenu();
            if (!CommandType.isValidCommandInput(inputCommand)) {
                consoleView.showCommandError();
                continue;
            }

            CommandType commandType = CommandType.getCommandType(inputCommand);
            switch (commandType) {
                case EXIT -> isProgramRunnable = false;
                case CREATE -> {
                    consoleView.showVoucherType();

                    String inputVoucherType = consoleView.requestVoucherType();
                    if (!VoucherType.isValidVoucherTypeInput(inputVoucherType)) {
                        consoleView.showVoucherTypeError();
                        continue;
                    }

                    VoucherType voucherType = VoucherType.getVoucherTypeByCommand(inputVoucherType);
                    String inputAmount = consoleView.getAmount(voucherType);
                    if (!voucherType.validateAmount(inputAmount)) {
                        consoleView.showAmountError();
                        continue;
                    }
                    voucherService.createVoucher(voucherType, inputAmount);
                }
                case LIST -> consoleView.showAllVouchers(voucherService.findAllVouchers());
            }
        }
    }
}
