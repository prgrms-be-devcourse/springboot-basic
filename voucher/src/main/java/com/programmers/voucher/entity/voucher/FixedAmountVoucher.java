package com.programmers.voucher.entity.voucher;

public class FixedAmountVoucher extends Voucher {

    private int amount;

    public FixedAmountVoucher(long id, String name, int amount) {
        super(id, name);
        this.amount = amount;
    }

    public FixedAmountVoucher(String name, int amount) {
        super(name);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return String.format("[Voucher #%d] %s / %d / FIXED", super.id, super.name, amount);
    }

    @Override
    public int discount(int original) {
        return Math.max(0, original - amount);
    }
}
