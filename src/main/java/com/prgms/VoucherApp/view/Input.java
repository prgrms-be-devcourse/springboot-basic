package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.voucher.VoucherType;

public interface Input {
    String inputCommand();

    String inputVoucherType();

    Long inputDiscountAmount(VoucherType voucherType);
}
