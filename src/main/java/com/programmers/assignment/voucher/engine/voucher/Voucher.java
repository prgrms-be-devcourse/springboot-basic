package com.programmers.assignment.voucher.engine.voucher;

import java.util.UUID;

public abstract class Voucher {
    UUID voucherId;
    String discountWay;
    long discountValue;
    UUID customerId;

    public Voucher(UUID voucherId, String discountWay, long discountValue, UUID customerId) {
        this.voucherId = voucherId;
        this.discountWay = discountWay;
        this.discountValue = discountValue;
        this.customerId = customerId;
    }

    abstract UUID getVoucherId();
    abstract long discount(long beforeDiscount);
}