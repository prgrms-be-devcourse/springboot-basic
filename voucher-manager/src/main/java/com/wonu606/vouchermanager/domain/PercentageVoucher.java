package com.wonu606.vouchermanager.domain;

import java.util.UUID;

public class PercentageVoucher extends Voucher {

    private final double percentage;

    public PercentageVoucher(UUID uuid, double percentage) {
        super(uuid);

        validatePercentage(percentage);
        this.percentage = percentage;
    }

    private void validatePercentage(double percentage) {
        if (isInvalidPercentage(percentage)) {
            throw new IllegalArgumentException("할인율은 0에서 100 사이여야 합니다.");
        }
    }

    private static boolean isInvalidPercentage(double percentage) {
        return percentage < 0  ||  100 < percentage;
    }

    @Override
    public double calculateDiscountedPrice(double originalPrice) {
        return originalPrice * (1 - percentage / 100);
    }
}
