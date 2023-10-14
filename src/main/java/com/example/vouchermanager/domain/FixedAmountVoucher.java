package com.example.vouchermanager.domain;

public class FixedAmountVoucher implements Voucher {

    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
    public String toString() {
        return "Voucher type: fixed\nDiscount: " + amount;
    }
}
