package org.prgrms.spring_week1.models;

public class FixedAmountVoucher implements Voucher{
    private long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount-amount;
    }
}
