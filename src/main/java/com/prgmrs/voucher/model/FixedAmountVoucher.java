package com.prgmrs.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (amount > beforeDiscount) {
            return 0;
        }
        return beforeDiscount - amount;
    }
}
