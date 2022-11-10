package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MINPERCENT = 0L;
    private static final long MAXPERCENT = 100L;
    private final UUID voucherId;
    private final long discountPercent;

    public PercentDiscountVoucher(long discountPercent) {
        this(UUID.randomUUID(), discountPercent);
    }

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
        valid(discountPercent);
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    private void valid(long discountAmount) {
        if (MINPERCENT >= discountAmount || discountAmount >= MAXPERCENT) {
            throw new RuntimeException("적절한 할인 범위를 넘어갔습니다.");
        }
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public long getDiscount() {
        return discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - ((beforeDiscount * discountPercent) / 100);
    }


    @Override
    public String toString() {
        return "VoucherType: PercentDiscountVoucher, " +
                "voucherId: " + voucherId +
                ", discountPercent: " + discountPercent + "%";
    }
}
