package com.ray.junho.voucher.controller;

import com.ray.junho.voucher.service.VoucherService;
import com.ray.junho.voucher.util.StringUtil;
import com.ray.junho.voucher.view.Console;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommandLineController {

    private final VoucherService voucherService;
    private final Console console;

    public CommandLineController(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    public void run() {
        while (true) {
            console.printStartMessage();
            switch (Command.find(console.read())) {
                case EXIT -> {
                    console.printExitMessage();
                    return;
                }
                case LIST -> {
                    List<String> vouchers = StringUtil.convertVouchersToList(voucherService.findAll());
                    console.print(vouchers);
                }
                case CREATE -> {
                    int voucherType = console.readVoucherTypeToCreate();
                    int discountAmount = console.readVoucherDiscountAmount(voucherType);
                    int voucherAmount = console.readVoucherAmountToCreate();
                    voucherService.create(voucherType, discountAmount, voucherAmount);
                }
            }
        }
    }
}
