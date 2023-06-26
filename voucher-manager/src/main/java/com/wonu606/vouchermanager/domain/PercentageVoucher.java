package com.wonu606.vouchermanager.domain;

import java.util.UUID;

public class PercentageVoucher extends Voucher {

    private final double percentage;

    public PercentageVoucher(UUID uuid, double percentage) {
        super(uuid);

        validatePercentage(percentage);
        this.percentage = percentage;
    }

    private static void validatePercentage(double percentage) {
        if (!isPercentageValid(percentage)) {
            throw new IllegalArgumentException("할인율은 0에서 100 사이여야 합니다.");
        }
    }

    private static boolean isPercentageValid(double percentage) {
        return 0 <= percentage && percentage <= 100;
    }

    @Override
    public double calculateDiscountedPrice(double originalPrice) {
        return originalPrice * (1 - percentage / 100);
    }
}
