package com.demo.voucher.io;

import com.demo.voucher.domain.VoucherType;

public interface Input {
    String requestMenu();

    String requestVoucherType();

    String getAmount(VoucherType voucherType);
}
