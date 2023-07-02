package com.programmers.springbasic.domain.voucher.entity;

import com.programmers.springbasic.domain.voucher.model.VoucherOption;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(double fixedValue) {
        super();
        this.value = fixedValue;
        this.voucherType = VoucherOption.FIXED_AMOUNT_VOUCHER;
    }
}
