package com.programmers.part1.order.voucher.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherID;
    private final long amount;

    public FixedAmountVoucher(UUID voucherID, long amount) {
        this.voucherID = voucherID;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "amount=" + amount +
                '}';
    }
}
