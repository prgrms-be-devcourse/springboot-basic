package com.devcourse.springbootbasic.engine.voucher.domain;

import com.devcourse.springbootbasic.engine.model.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final double discountPercent;

    public PercentDiscountVoucher(UUID voucherId, VoucherType voucherType, double percent) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountPercent = percent;
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - (1 - discountPercent / (double) 100);
    }

    @Override
    public String toString() {
        return voucherType.name() + " -> id: " + voucherId +
                ", discount" + voucherType.getTypeString() +
                ": " + discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
