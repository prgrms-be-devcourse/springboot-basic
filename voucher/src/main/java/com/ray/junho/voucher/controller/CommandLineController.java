package com.ray.junho.voucher.controller;

import com.ray.junho.voucher.repository.VoucherRepository;
import com.ray.junho.voucher.service.VoucherService;
import com.ray.junho.voucher.view.Console;
import org.springframework.stereotype.Controller;

@Controller
public class CommandLineController {

    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;
    private final Console console;

    public CommandLineController(VoucherService voucherService, VoucherRepository voucherRepository, Console console) {
        this.voucherService = voucherService;
        this.voucherRepository = voucherRepository;
        this.console = console;
    }
}
