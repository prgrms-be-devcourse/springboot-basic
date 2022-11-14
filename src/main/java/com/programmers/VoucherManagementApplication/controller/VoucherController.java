package com.programmers.VoucherManagementApplication.controller;

import com.programmers.VoucherManagementApplication.io.Console;
import com.programmers.VoucherManagementApplication.io.MenuType;
import com.programmers.VoucherManagementApplication.io.Message;
import com.programmers.VoucherManagementApplication.repository.MemoryVoucherRepository;
import com.programmers.VoucherManagementApplication.service.VoucherService;
import com.programmers.VoucherManagementApplication.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherController implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    private final Console console;
    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        try {
            while (true) {
                console.menuPromptPrint();
                MenuType menuType = MenuType.getMenuType(console.input());
                switch (menuType) {
                    case EXIT:
                        console.exitMenuPrint();
                        return;
                    case CREATE:
                        VoucherType voucherType = inputVoucherType();
                        long originPrice = inputOriginPrice();
                        long amount = inputVoucherAmount(voucherType);
                        voucherService.create(originPrice, amount, voucherType);
                        break;
                    case LIST:
                        console.findAll(voucherService.findAll());
                        break;
                }
            }
        } catch (Exception e) {
            logger.error(String.valueOf(Message.INVALID_INPUT));
            console.invalidInputPrint();
            run();
        }

    }

    private Long inputVoucherAmount(VoucherType voucherType) {
        console.inputVoucherAmount(voucherType.getInputMessage());
        return Long.parseLong(console.input());
    }

    private Long inputOriginPrice() {
        console.inputPricePrompt();
        return Long.parseLong(console.input());
    }

    private VoucherType inputVoucherType() {
        console.selectVoucherMenu();
        String input = console.input().toUpperCase();
        return validateVoucherType(input);
    }

    private VoucherType validateVoucherType(String input) {
        return VoucherType.of(input);
    }
}
