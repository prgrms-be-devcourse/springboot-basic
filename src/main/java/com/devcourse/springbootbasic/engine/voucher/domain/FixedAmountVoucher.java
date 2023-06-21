package com.devcourse.springbootbasic.engine.voucher.domain;

import com.devcourse.springbootbasic.engine.model.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final double discountAmount;

    public FixedAmountVoucher(UUID voucherId, VoucherType voucherType, double discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - discountAmount;
    }

    @Override
    public String toString() {
        return voucherType.name() + " -> id: " + voucherId +
                ", discount" + voucherType.getTypeString() +
                ": " + discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
