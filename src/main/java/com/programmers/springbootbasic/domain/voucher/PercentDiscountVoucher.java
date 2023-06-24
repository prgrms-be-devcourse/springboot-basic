package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, expirationDate);
        if (isInvalidateRange(percent)) {
            throw new IllegalArgumentException("잘못된 할인 범위");
        }
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, minimumPriceCondition, expirationDate);
        if (isInvalidateRange(percent)) {
            throw new IllegalArgumentException("잘못된 할인 범위");
        }
        this.percent = percent;
    }

    @Override
    public Long getDiscountPrice(Long priceBeforeDiscount) {
        return Math.round((priceBeforeDiscount - (priceBeforeDiscount * percent / 100d)) / 10) * 10;
    }

    @Override
    public Long discount(Long priceBeforeDiscount) {
        setVoucherState(VoucherState.USED);
        return getDiscountPrice(priceBeforeDiscount);
    }

    private Boolean isInvalidateRange(int percent) {
        int MIN_PERCENT = 1;
        int MAX_PERCENT = 100;
        return percent < MIN_PERCENT || MAX_PERCENT < percent;
    }
}