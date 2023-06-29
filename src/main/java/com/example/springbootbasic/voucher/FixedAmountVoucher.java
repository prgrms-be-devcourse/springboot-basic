package com.example.springbootbasic.voucher;

public class FixedAmountVoucher implements Voucher {
    private final long fixedAmount;

    public FixedAmountVoucher(long fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - fixedAmount;
    }

    public long getFixedAmount() {
        return fixedAmount;
    }
}
