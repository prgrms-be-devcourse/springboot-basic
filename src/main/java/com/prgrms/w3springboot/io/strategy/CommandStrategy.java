package com.prgrms.w3springboot.io.strategy;

import com.prgrms.w3springboot.io.Console;
import com.prgrms.w3springboot.voucher.service.VoucherService;

@FunctionalInterface
public interface CommandStrategy {
    boolean execute(Console console, VoucherService voucherService);
}
