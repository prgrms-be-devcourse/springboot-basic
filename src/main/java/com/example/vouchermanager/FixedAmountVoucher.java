package com.example.vouchermanager;

public class FixedAmountVoucher implements Voucher {

    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
