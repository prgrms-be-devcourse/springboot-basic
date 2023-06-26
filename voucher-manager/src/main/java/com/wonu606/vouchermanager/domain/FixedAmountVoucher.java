package com.wonu606.vouchermanager.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final double fixedAmount;

    public FixedAmountVoucher(UUID uuid, double fixedAmount) {
        super(uuid);
        this.fixedAmount = fixedAmount;
    }

    @Override
    public double calculateDiscountedPrice(double originalPrice) {
        return Math.max(originalPrice - fixedAmount, 0);
    }
}
