package com.programmers.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final long discountAmount;

    public Voucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    abstract long discount(long beforeAmount);

}
