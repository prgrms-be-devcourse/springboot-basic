package com.demo.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final String unit = "원";

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public String getVoucherTypeDescription() {
        return VoucherType.FIXED_AMOUNT.getVoucherDescription();
    }

    @Override
    public String getDiscountInfo() {
        return amount + unit;
    }
}
