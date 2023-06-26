package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final int percent;
    static final int MIN_PERCENT = 1;
    static final int MAX_PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, int percent) {
        this(voucherId, name, 0L, expirationDate, percent);
    }

    public PercentDiscountVoucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, minimumPriceCondition, expirationDate);
        if (isInvalidRange(percent)) {
            throw new IllegalArgumentException("잘못된 할인 범위, percent=" + percent);
        }
        this.percent = percent;
    }

    @Override
    public Long getDiscountPrice(Long priceBeforeDiscount) {
        int tenForRound = 10;
        double discountAmount = priceBeforeDiscount * percent / (double) MAX_PERCENT;
        double discountedPrice = priceBeforeDiscount - discountAmount;
        return Math.round((discountedPrice) / tenForRound) * tenForRound;
    }

    private boolean isInvalidRange(int percent) {
        return percent < MIN_PERCENT || MAX_PERCENT < percent;
    }
}
