package com.programmers.vouchermanagement.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;

    private final String voucherName;
    private final float discountAmount;

    public PercentDiscountVoucher(String voucherName, float discountAmount) {
        this.voucherId = UUID.randomUUID();
        this.voucherName = voucherName;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getId() {
        return this.voucherId;
    }

    @Override
    public float discount(float beforeDiscount) {
        float afterDiscount = beforeDiscount - (beforeDiscount * discountAmount / 100);
        return afterDiscount < 0 ? 0 : afterDiscount;
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "=======================" + System.lineSeparator() +
                "Voucher Id:    " + voucherId + System.lineSeparator() +
                "Voucher Name:  " + voucherName + System.lineSeparator() +
                "Voucher Type:  Percentage Discount voucher" + System.lineSeparator() +
                "Discount percentage: " + discountAmount + System.lineSeparator();
    }
}
