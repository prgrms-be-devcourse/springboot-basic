package com.demo.voucher.controller;

import com.demo.voucher.domain.VoucherType;
import com.demo.voucher.io.CommandType;
import com.demo.voucher.io.ConsoleView;
import com.demo.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class VoucherController implements Runnable {
    private static final String EXIT = "exit";
    private static final String CREATE_VOUCHER = "create";
    private static final String LIST_ALL_VOUCHERS = "list";

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

            switch (inputCommand) {
                case EXIT -> isProgramRunnable = false;
                case CREATE_VOUCHER -> {
                    consoleView.showVoucherType();

                    String inputVoucherType = consoleView.requestVoucherType();
                    Optional<VoucherType> voucherType = VoucherType.getVoucherTypeByCommand(inputVoucherType);
                    if (voucherType.isEmpty()) {
                        consoleView.showVoucherTypeError();
                        continue;
                    }

                    String inputAmount = consoleView.getAmount(voucherType.get());
                    if (!voucherType.get().validateAmount(inputAmount)) {
                        consoleView.showAmountError();
                        continue;
                    }
                    voucherService.createVoucher(voucherType.get(), inputAmount);
                }
                case LIST_ALL_VOUCHERS -> consoleView.showAllVouchers(voucherService.findAllVouchers());
            }
        }
    }
}
