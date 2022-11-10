package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import java.util.UUID;

public class FixedAmountDiscountVoucher implements Voucher {

    private static final long MINAMOUNT = 0L;
    private static final long MAXAMOUNT = 100000L;
    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountDiscountVoucher(long discountAmount) {
        this(UUID.randomUUID(), discountAmount);
    }

    public FixedAmountDiscountVoucher(UUID voucherId, long discountAmount) {
        valid(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;

    }

    private void valid(long discountAmount) {
        if (MINAMOUNT >= discountAmount || discountAmount >= MAXAMOUNT) {
            throw new RuntimeException("적절한 할인 범위를 넘어갔습니다.");
        }
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public long getDiscount() {
        return discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    @Override
    public String toString() {
        return "VoucherType: FixedAmountDiscountVoucher, " +
                "voucherId: " + voucherId +
                ", discountPercent: " + discountAmount + "$";
    }

}
