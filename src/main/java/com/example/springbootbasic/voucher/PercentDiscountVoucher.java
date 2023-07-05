package com.example.springbootbasic.voucher;

import java.text.MessageFormat;

public class PercentDiscountVoucher implements Voucher {

    private final long percent;

    public PercentDiscountVoucher(long percent) {
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (percent / 100);
    }

    @Override
    public String getVoucherInfo() {
        return MessageFormat.format("VoucherType: {0}, percent: {1}%", this.getClass().getName(), percent);
    }

    public long getPercent() {
        return percent;
    }
}
