package com.prgms.voucher.voucherproject.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final static long MIN_AMOUNT = 0;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= MIN_AMOUNT) {
            throw new IllegalArgumentException("잘못된 고정 할인 금액입니다.");
        }
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getId() {
        return this.voucherId;
    }

    public long getAmount() {
        return this.amount;
    }

    public static boolean isFixedAmountVoucher(Voucher voucher) {
        return voucher instanceof FixedAmountVoucher;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - this.amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

}
