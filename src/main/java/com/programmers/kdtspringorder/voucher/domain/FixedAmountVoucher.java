package com.programmers.kdtspringorder.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }
        if (amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        }

        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public long getValue() {
        return amount;
    }

    @Override
    public String toString() {
        return "voucherId=" + voucherId +
                ", discountAmount=" + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedAmountVoucher that = (FixedAmountVoucher) o;

        if (amount != that.amount) return false;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        int result = voucherId.hashCode();
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }
}
