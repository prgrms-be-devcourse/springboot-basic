package com.prgrms.w3springboot.io.strategy;

import com.prgrms.w3springboot.io.Input;
import com.prgrms.w3springboot.io.Output;
import com.prgrms.w3springboot.voucher.service.VoucherService;

@FunctionalInterface
public interface CommandStrategy {
    boolean execute(Input input, Output output, VoucherService voucherService);
}
