package com.programmers.springweekly.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private final UUID voucherId;
    private final long discountPercent;
    private final String voucherType;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent, String voucherType) {
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (discountPercent / 100);
    }

    @Override
    public long getVoucherAmount() {
        return discountPercent;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }
}
