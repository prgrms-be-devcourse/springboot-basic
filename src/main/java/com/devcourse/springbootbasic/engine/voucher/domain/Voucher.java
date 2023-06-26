package com.devcourse.springbootbasic.engine.voucher.domain;

import com.devcourse.springbootbasic.engine.model.VoucherType;

import java.util.UUID;

public abstract class Voucher {
    UUID voucherId;
    VoucherType voucherType;
    double discountValue;

    Voucher(UUID voucherId, VoucherType voucherType, double discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountAmount;
    }

    abstract double discountedPrice(long originalPrice);

    public UUID getVoucherId() {
        return voucherId;
    }

    public String toString() {
        return voucherType.name() + " -> " +
                "id:" + voucherId +
                ", discount" + voucherType.getTypeString() +
                ":" + discountValue;
    }
}
