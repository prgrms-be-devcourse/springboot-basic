package com.dev.bootbasic.voucher.domain;

import java.util.UUID;

import static com.dev.bootbasic.voucher.domain.VoucherType.FIXED;

public class FixedAmountVoucher extends Voucher {

    public static final String FIXED_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE = "입력하신 금액이 조건에 맞지 않습니다.";
    private static final int MINIMUM_DISCOUNTED_PRICE = 0;

    private FixedAmountVoucher(UUID id, VoucherType voucherType, int discountAmount) {
        super(id, voucherType, discountAmount);
    }

    public static FixedAmountVoucher of(UUID id, int discountAmount) {
        validateAmount(discountAmount);
        return new FixedAmountVoucher(id, FIXED, discountAmount);
    }

    private static void validateAmount(int discountAmount) {
        if (discountAmount < FIXED.getMinimumAmount() || FIXED.getMaximumAmount() < discountAmount) {
            throw new IllegalArgumentException(FIXED_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public int discount(int productPrice) {
        int discountedPrice = productPrice - getDiscountAmount();

        return Math.max(discountedPrice, MINIMUM_DISCOUNTED_PRICE);
    }

}
