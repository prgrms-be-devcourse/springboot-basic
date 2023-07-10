package com.programmers.springbasic.domain.voucher.entity;

import com.programmers.springbasic.domain.voucher.view.VoucherOption;

import java.time.LocalDate;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(double fixedValue) {
        super();
        this.value = fixedValue;
        this.voucherType = VoucherOption.FIXED_AMOUNT_VOUCHER;
    }

    public FixedAmountVoucher(double fixedValue, UUID customerId) {
        super();
        this.value = fixedValue;
        this.voucherType = VoucherOption.FIXED_AMOUNT_VOUCHER;
        this.customerId = customerId;
    }

    public FixedAmountVoucher(UUID voucherCode, double value, LocalDate expirationDate, boolean isActive, UUID customerId) {
        this.code = voucherCode;
        this.value = value;
        this.voucherType = VoucherOption.FIXED_AMOUNT_VOUCHER;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
        this.customerId = customerId;
    }
}
