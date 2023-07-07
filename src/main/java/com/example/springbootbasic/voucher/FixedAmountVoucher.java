package com.example.springbootbasic.voucher;

import java.text.MessageFormat;

public class FixedAmountVoucher implements Voucher {
    private final long fixedAmount;

    public FixedAmountVoucher(String fixedAmount) {
        this.fixedAmount = Long.parseLong(fixedAmount);
        validateInput();
    }

    private void validateInput() {
        if (this.fixedAmount < 0) {
            throw new IllegalStateException("FixedAmount cannot be negative");
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - fixedAmount;
    }

    @Override
    public void printInfo() {
        System.out.println(MessageFormat.format("VoucherType: {0}, percent: {1}$", this.getClass().getName(), fixedAmount));
    }

    public long getFixedAmount() {
        return fixedAmount;
    }
}
