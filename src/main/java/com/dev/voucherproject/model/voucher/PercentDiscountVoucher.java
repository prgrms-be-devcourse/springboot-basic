package com.dev.voucherproject.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, LocalDateTime createdAt, long percent) {
        super(voucherId, createdAt);
        percentValidate(percent);
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        double percentage = getDoubleTypePercentage(percent);
        return (long) (beforeDiscount - percentage * beforeDiscount);
    }

    @Override
    public long getDiscountFigure() {
        return this.percent;
    }

    private double getDoubleTypePercentage(long percent) {
        return (double) percent / 100;
    }

    private void percentValidate(long percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("할인률은 0~100% 사이에서 결정되어야 합니다.");
        }
    }
}
