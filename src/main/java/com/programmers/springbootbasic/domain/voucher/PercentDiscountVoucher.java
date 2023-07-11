package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.service.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private static final long ZERO = 0;
    private static final int MIN_PERCENT = 1;
    private static final int MAX_PERCENT = 100;
    private static final String INVALID_PERCENT = String.format(
            "잘못된 할인율입니다. 할인율은 최소 %d부터 최대 %d까지 입니다. 현재 입력 퍼센트: ", MIN_PERCENT, MAX_PERCENT
    );

    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, VoucherType voucherType, String name, VoucherDateTime voucherDateTime, int percent) {
        this(voucherId, voucherType, name, ZERO, voucherDateTime, percent);
    }

    public PercentDiscountVoucher(UUID voucherId, VoucherType voucherType, String name, Long minimumPriceCondition, VoucherDateTime voucherDateTime, int percent) {
        super(voucherId, voucherType, name, minimumPriceCondition, voucherDateTime);
        if (isInvalidRange(percent)) {
            throw new IllegalArgumentException(INVALID_PERCENT + String.format("%d%%", percent));
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
