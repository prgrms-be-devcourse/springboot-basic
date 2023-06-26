package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final int percent;
    static final int MIN_PERCENT = 1;
    static final int MAX_PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, String name, LocalDateTime createdDate, LocalDateTime expirationDate, int percent) {
        this(voucherId, name, 0L, createdDate, expirationDate, percent);
    }

    public PercentDiscountVoucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime createdDate, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, minimumPriceCondition, createdDate, expirationDate);
        if (isInvalidRange(percent)) {
            throw new IllegalArgumentException("" +
                    "잘못된 할인율입니다. " +
                    "할인율은 최소 " + MIN_PERCENT + "%부터 최대 " + MAX_PERCENT + "%까지 입니다. " +
                    "현재 입력 퍼센트: " + percent + "%"
            );
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
