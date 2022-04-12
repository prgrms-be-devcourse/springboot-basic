package com.mountain.voucherApp.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount should be positive");
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("Amount should be less than " + MAX_VOUCHER_AMOUNT);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public FixedAmountVoucher(long amount) {
        FixedAmountVoucher instance = new FixedAmountVoucher(UUID.randomUUID(), amount);
        this.voucherId = instance.getVoucherId();
        this.amount = instance.getAmount();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return "id: " + voucherId + ", amount: " + amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }
}
