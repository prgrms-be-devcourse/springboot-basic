package com.programmers.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final String voucherName;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, String voucherName, long amount) {
        if (voucherName.isEmpty() || amount < 0) {
            throw new IllegalArgumentException();
        }

        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getVoucherName() {
        return voucherName;
    }

    @Override
    public long getDiscountValue() {
        return amount;
    }

    @Override
    public String toString() {
        return "Fixed Amount Voucher [" +
                "Id = " + voucherId +
                ", voucher name = " + voucherName +
                ", discount amount = " + amount + ']';
    }
}