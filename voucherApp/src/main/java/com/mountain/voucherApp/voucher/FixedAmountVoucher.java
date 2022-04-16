package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.enums.DiscountPolicy;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    @Override
    public boolean validate(long discountAmount) {
        // MAX_AMOUNT 검사
        if (discountAmount <= 0)
            throw new IllegalArgumentException("Amount should be positive");
        if (discountAmount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("Amount should be less than " + MAX_VOUCHER_AMOUNT);
        return true;
    }

    @Override
    public long discount(long beforeDiscount, long discountAmount) {
        long discountedAmount = beforeDiscount - discountAmount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }
}
