package com.wonu606.vouchermanager.domain;

import java.util.UUID;
import lombok.ToString;

@ToString
public class PercentageVoucher extends Voucher {

    private final double percentage;

    public PercentageVoucher(UUID uuid, double percentage) {
        super(uuid);

        validatePercentage(percentage);
        this.percentage = percentage;
    }

    private boolean isInvalidPercentage(double percentage) {
        return percentage < 0 || 100 < percentage;
    }

    private void validatePercentage(double percentage) {
        if (isInvalidPercentage(percentage)) {
            throw new IllegalArgumentException("할인율은 0에서 100 사이여야 합니다.");
        }
    }

    @Override
    public double calculateDiscountedPrice(double originalPrice) {
        return originalPrice * (1 - percentage / 100);
    }
}
