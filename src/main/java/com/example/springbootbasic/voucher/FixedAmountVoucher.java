package com.example.springbootbasic.voucher;

import java.text.MessageFormat;

public class FixedAmountVoucher implements Voucher {
    private final long fixedAmount;

    public FixedAmountVoucher(long fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - fixedAmount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("VoucherType: {0}, percent: {1}$", this.getClass().getName(), fixedAmount);
    }

    public long getFixedAmount() {
        return fixedAmount;
    }
}
