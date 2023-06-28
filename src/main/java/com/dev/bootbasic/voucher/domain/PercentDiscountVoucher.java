package com.dev.bootbasic.voucher.domain;

import java.util.UUID;

import static com.dev.bootbasic.voucher.domain.VoucherType.PERCENT;

public class PercentDiscountVoucher extends Voucher {

    private static final String PERCENT_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE = "입력하신 퍼센트가 조건에 맞지 않습니다.";
    private static final int MAX_PERCENT = 100;

    private PercentDiscountVoucher(UUID id, VoucherType voucherType, int discountAmount) {
        super(id, voucherType, discountAmount);
    }

    public static PercentDiscountVoucher of(UUID id, int discountAmount) {
        validateAmount(discountAmount);
        return new PercentDiscountVoucher(id, PERCENT, discountAmount);
    }

    private static void validateAmount(int discountAmount) {
        if (discountAmount < PERCENT.getMinimumAmount() || PERCENT.getMaximumAmount() < discountAmount) {
            throw new IllegalArgumentException(PERCENT_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public int discount(int originPrice) {
        return originPrice - (originPrice * getDiscountAmount() / MAX_PERCENT);
    }

}
