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
            throw new IllegalStateException(MessageFormat.format("할인금액은 음수가 될 수 없습니다. 입력한 금액 : {}", this.fixedAmount));
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - fixedAmount;
    }

    @Override
    public void printInfo() {
        System.out.println(MessageFormat.format("VoucherType: {0}, fixedAmount: {1}$", this.getClass().getSimpleName(), this.fixedAmount));
    }

    public long getFixedAmount() {
        return fixedAmount;
    }
}
