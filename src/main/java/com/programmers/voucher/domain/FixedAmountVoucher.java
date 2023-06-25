package com.programmers.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final int MAX_DISCOUNT_AMOUNT = 5000;
    private static final int MIN_DISCOUNT_AMOUNT = 0;

    private final UUID voucherId;
    private final long discountAmount;

    protected FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    public static Voucher of(UUID voucherId, long discountAmount) {
        validateDiscountAmount(discountAmount);
        return new FixedAmountVoucher(voucherId, discountAmount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public long discount(long originPrice) {
        if (originPrice < getDiscountAmount()) {
            return 0;
        }
        return originPrice - getDiscountAmount();
    }

    private static void validateDiscountAmount(long discountAmount) {
        if (discountAmount > MAX_DISCOUNT_AMOUNT || discountAmount < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(MIN_DISCOUNT_AMOUNT + " ~ " + MAX_DISCOUNT_AMOUNT + " 범위의 바우처 할인양을 입력해주세요. " + "입력값: " + discountAmount);
        }
    }

}
