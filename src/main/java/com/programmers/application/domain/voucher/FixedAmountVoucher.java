package com.programmers.application.domain.voucher;

import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final int MAX_DISCOUNT_AMOUNT = 5000;
    private static final int MIN_DISCOUNT_AMOUNT = 0;

    private FixedAmountVoucher(UUID voucherId, long discountAmount) {
        super(voucherId, discountAmount);
    }

    protected static Voucher of(UUID voucherId, long discountAmount) {
        validateVoucherId(voucherId);
        validateDiscountAmount(discountAmount);
        return new FixedAmountVoucher(voucherId, discountAmount);
    }

    @Override
    public long discount(long originalPrice) {
        return Math.max(originalPrice - getDiscountAmount(), 0);
    }

    private static void validateVoucherId(UUID voucherId) {
        if (Objects.isNull(voucherId)) {
            throw new IllegalArgumentException("바우처 아이디가 비어있습니다.");
        }
    }

    private static void validateDiscountAmount(long discountAmount) {
        if (discountAmount > MAX_DISCOUNT_AMOUNT || discountAmount < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(MIN_DISCOUNT_AMOUNT + " ~ " + MAX_DISCOUNT_AMOUNT + " 범위의 바우처 할인양을 입력해주세요. " + "입력값: " + discountAmount);
        }
    }
}
