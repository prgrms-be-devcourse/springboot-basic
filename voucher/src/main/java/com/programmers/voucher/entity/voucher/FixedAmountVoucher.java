package com.programmers.voucher.entity.voucher;

public class FixedAmountVoucher extends Voucher {

    int amount;

    public FixedAmountVoucher(long id, String name, int amount) {
        super(id, name);
        this.amount = amount;
    }

    @Override
    public int discount(int original) {
        return Math.max(0, original - amount);
    }
}
