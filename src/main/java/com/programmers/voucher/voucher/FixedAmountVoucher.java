package com.programmers.voucher.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(long amount, UUID voucherId) {
        this.amount = amount;
        this.voucherId = voucherId;
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
        return "type=FixedAmountVoucher" + '\n' +
                "amount=" + amount;
    }

    @Override
    public long getValue() {
        return amount;
    }
}
