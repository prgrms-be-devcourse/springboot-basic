package com.prgrms.springbootbasic.controller;

import com.prgrms.springbootbasic.io.Input;
import com.prgrms.springbootbasic.io.Output;
import com.prgrms.springbootbasic.service.VoucherService;

public class FixedDiscountVoucherController {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public FixedDiscountVoucherController(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }
}
