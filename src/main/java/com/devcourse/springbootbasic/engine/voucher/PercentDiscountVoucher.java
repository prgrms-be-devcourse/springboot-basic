package com.devcourse.springbootbasic.engine.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final double discountPercent;

    public PercentDiscountVoucher(UUID voucherId, double percent) {
        this.voucherId = voucherId;
        this.discountPercent = percent;
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - (1 - discountPercent / (double) 100);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
