package org.prgrms.orderapp.model;

import java.io.Serializable;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount should be positive");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("amount should be less than " + MAX_VOUCHER_AMOUNT);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount > 0 ? beforeDiscount - amount : 0;
    }

    @Override
    public String toString() {
        return "FixAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public long getAmount() {
        return this.amount;
    }
}
