package com.programmers.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final String voucherName;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, String voucherName, long percent) {
        if (voucherName.isEmpty() || percent < 1 || percent > 100) {
            throw new IllegalArgumentException();
        }

        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.percent = percent;
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
        return percent;
    }

    @Override
    public String toString() {
        return "Percent Discount Voucher [" +
                "Id = " + voucherId +
                ", voucher name = " + voucherName +
                ", discount percent = " + percent + ']';
    }
}