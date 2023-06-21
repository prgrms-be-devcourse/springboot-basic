package com.devcourse.springbootbasic.engine.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
