package com.wonu606.vouchermanager.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final double fixedAmount;

    public FixedAmountVoucher(UUID uuid, double fixedAmount) {
        super(uuid);
        validateFixedAmount(fixedAmount);
        this.fixedAmount = fixedAmount;
    }

    @Override
    public double calculateDiscountedPrice(double originalPrice) {
        return Math.max(originalPrice - fixedAmount, 0);
    }

    private void validateFixedAmount(double fixedAmount) {
        if (!isFixedAmountValid(fixedAmount)) {
            throw new IllegalArgumentException("할인할 금액은 양수여야 합니다.");
        }
    }

    private boolean isFixedAmountValid(double fixedAmount) {
        return fixedAmount >= 0;
    }
}
