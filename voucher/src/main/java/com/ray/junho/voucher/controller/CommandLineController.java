package com.ray.junho.voucher.controller;

import com.ray.junho.voucher.service.VoucherService;
import com.ray.junho.voucher.view.Console;
import org.springframework.stereotype.Controller;

@Controller
public class CommandLineController {

    private final VoucherService voucherService;
    private final Console console;

    public CommandLineController(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }
}
