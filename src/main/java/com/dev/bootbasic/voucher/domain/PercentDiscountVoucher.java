package com.dev.bootbasic.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends AbstractVoucher {

    public static final int PERCENT_MINIMUM_DISCOUNT_AMOUNT = 1;
    public static final int PERCENT_MAXIMUM_DISCOUNT_AMOUNT = 100;
    public static final String PERCENT_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE = "백분율할인 바우처는" + PERCENT_MINIMUM_DISCOUNT_AMOUNT + "이상" + PERCENT_MAXIMUM_DISCOUNT_AMOUNT + "이하만 가능합니다.";
    private static final int MAX_PERCENT = 100;

    private PercentDiscountVoucher(UUID id, int discountAmount) {
        super(id, discountAmount);
    }

    public static PercentDiscountVoucher of(UUID id, int discountAmount) {
        validateAmount(discountAmount);
        return new PercentDiscountVoucher(id, discountAmount);
    }

    private static void validateAmount(int discountAmount) {
        if (discountAmount < PERCENT_MINIMUM_DISCOUNT_AMOUNT || PERCENT_MAXIMUM_DISCOUNT_AMOUNT < discountAmount) {
            throw new IllegalArgumentException(PERCENT_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public int discount(int originPrice) {
        return originPrice - (originPrice * discountAmount/ MAX_PERCENT);
    }

}
