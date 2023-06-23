package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final int MIN_PERCENT = 1;
    private final int MAX_PERCENT = 100;
    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, expirationDate);
        if (isInvalidateRange(percent)) {
            throw new IllegalArgumentException("잘못된 할인 범위");
        }
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, String name, Long minimumPrice, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, minimumPrice, expirationDate);
        if (isInvalidateRange(percent)) {
            throw new IllegalArgumentException("잘못된 할인 범위");
        }
        this.percent = percent;
    }

    @Override
    public Long discount(Long priceBeforeDiscount) {
        return Math.round((priceBeforeDiscount - (priceBeforeDiscount * percent / 100d)) / 10) * 10;
    }

    private Boolean isInvalidateRange(int percent) {
        return percent < MIN_PERCENT || MAX_PERCENT < percent;
    }
}