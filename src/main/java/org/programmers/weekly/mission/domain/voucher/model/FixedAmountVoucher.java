package org.programmers.weekly.mission.domain.voucher.model;

import java.io.Serializable;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0 || amount == 0 || amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Amount should be positive and less than %d".formatted(MAX_VOUCHER_AMOUNT));
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public long getDiscount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (beforeDiscount < amount) {
            throw new IllegalArgumentException("beforeDiscount should not be less than amount");
        }

        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}