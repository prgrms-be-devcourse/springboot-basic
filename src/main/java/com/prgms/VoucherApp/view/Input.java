package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.VoucherType;

public interface Input {
    String inputCommand();

    String inputVoucherPolicy();

    Long inputDiscountAmount(VoucherType policy);
}
