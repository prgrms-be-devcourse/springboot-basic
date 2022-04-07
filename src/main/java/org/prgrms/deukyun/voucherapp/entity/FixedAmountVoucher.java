package org.prgrms.deukyun.voucherapp.entity;

public class FixedAmountVoucher {

    public final long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }
}
