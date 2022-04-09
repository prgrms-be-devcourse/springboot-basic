package me.programmers.springboot.basic.springbootbasic.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "amount=" + amount +
                ", voucherId=" + voucherId +
                '}';
    }
}
