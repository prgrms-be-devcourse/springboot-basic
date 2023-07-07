package com.example.springbootbasic.voucher;

import java.text.MessageFormat;

public class PercentDiscountVoucher implements Voucher {

    private final int percent;

    public PercentDiscountVoucher(String percent) throws NumberFormatException {
        this.percent = Integer.parseInt(percent);
        validateInput();
    }

    private void validateInput() {
        if (this.percent < 0 || this.percent > 100) {
            throw new IllegalStateException("Percent should be in range(0, 100)");
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (percent / 100);
    }

    @Override
    public void printInfo() {
        System.out.println(MessageFormat.format("VoucherType: {0}, percent: {1}%", this.getClass().getName(), percent));
    }

    public long getPercent() {
        return percent;
    }
}
