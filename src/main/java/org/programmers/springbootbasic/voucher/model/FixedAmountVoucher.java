package org.programmers.springbootbasic.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0) throw new IllegalArgumentException("amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("amount should not be zero");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("amount should be less than %d".formatted(MAX_VOUCHER_AMOUNT));
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher" +
                " voucherId: " + voucherId +
                " amount: " + amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }
}
