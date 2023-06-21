package com.example.demo.voucher.application;

@FunctionalInterface
public interface VoucherCommand {
    void execute(VoucherService voucherService);
}
