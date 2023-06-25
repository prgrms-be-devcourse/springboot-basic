package com.programmers.voucher.view;

public interface Input {
    Command readCommand();

    VoucherCommand readVoucherCommand();

    DiscountAmount readDiscountAmount(VoucherCommand voucherCommand);
}
