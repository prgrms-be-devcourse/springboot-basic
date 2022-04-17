package me.programmers.springboot.basic.springbootbasic.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId);
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + super.getVoucherId() +
                ", amount=" + amount +
                '}';
    }
}
