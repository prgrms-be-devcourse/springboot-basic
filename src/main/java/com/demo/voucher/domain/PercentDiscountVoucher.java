package com.demo.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final String UNIT = "%";

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(long percent) {
        this.voucherId = UUID.randomUUID();
        this.percent = percent;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getAmount() {
        return percent;
    }

    @Override
    public String getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT.getVoucherDescription();
    }

    @Override
    public String getDiscountInfo() {
        return percent + UNIT;
    }
}
