package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
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

    public long discount(long beforeDiscount) {
        if (beforeDiscount - amount < 0) {
            return 0;
        }

        return beforeDiscount - amount;
    }

    @Override
    public long getDiscountNumber() {
        return this.amount;
    }

    @Override
    public String toString() {
        return "FIXED_AMOUNT_VOUCHER,%d,%s".formatted(amount, voucherId);
    }
}
