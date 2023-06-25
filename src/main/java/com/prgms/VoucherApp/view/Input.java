package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.VoucherPolicy;

public interface Input {
    String inputCommand();

    String inputVoucherPolicy();

    Long inputDiscountAmount(VoucherPolicy policy);
}
