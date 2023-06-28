package com.example.demo.voucher.presentation.command;

import com.example.demo.voucher.application.VoucherService;

@FunctionalInterface
public interface VoucherCommand {
    void execute(VoucherService voucherService);
}
