package com.dev.bootbasic.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public static final int FIXED_MINIMUM_DISCOUNT_AMOUNT = 1_000;
    public static final int FIXED_MAXIMUM_DISCOUNT_AMOUNT = 100_000;
    public static final String FIXED_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE = "고정금액 바우처는" + FIXED_MINIMUM_DISCOUNT_AMOUNT + "초과" + FIXED_MAXIMUM_DISCOUNT_AMOUNT + "미만으로 가능합니다.";
    private static final int MINIMUM_DISCOUNTED_PRICE = 0;

    private FixedAmountVoucher(UUID id, int discountAmount) {
        super(id, discountAmount);
    }

    public static FixedAmountVoucher of(UUID id, int discountAmount) {
        validateAmount(discountAmount);
        return new FixedAmountVoucher(id, discountAmount);
    }

    private static void validateAmount(int discountAmount) {
        if (discountAmount < FIXED_MINIMUM_DISCOUNT_AMOUNT || FIXED_MAXIMUM_DISCOUNT_AMOUNT < discountAmount) {
            throw new IllegalArgumentException(FIXED_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public int discount(int productPrice) {
        int discountedPrice = productPrice - getDiscountAmount();

        return Math.max(discountedPrice, MINIMUM_DISCOUNTED_PRICE);
    }

}
