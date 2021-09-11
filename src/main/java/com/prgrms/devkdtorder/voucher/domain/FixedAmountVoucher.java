package com.prgrms.devkdtorder.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount, String name, LocalDateTime createdAt) {
        super(voucherId, amount, name, createdAt);
        validateAmount(amount);
    }

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this(voucherId, amount, "", LocalDateTime.now());
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - value;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + value +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    private void validateAmount(long amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("Amount should be less than " + MAX_VOUCHER_AMOUNT);
    }
}
