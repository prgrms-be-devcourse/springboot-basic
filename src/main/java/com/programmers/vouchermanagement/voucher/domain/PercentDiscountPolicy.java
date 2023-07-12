package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.VoucherErrorCode;
import com.programmers.vouchermanagement.voucher.exception.VoucherException;

import java.math.BigDecimal;

public class PercentDiscountPolicy extends DiscountPolicy {

    public static final int MIN_PERCENT = 1;
    public static final int MAX_PERCENT = 100;

    public PercentDiscountPolicy(int amount) {
        super(amount);
    }

    @Override
    void validateAmount(int amount) {
        if (MIN_PERCENT > amount || amount > MAX_PERCENT) {
            throw new VoucherException(VoucherErrorCode.INVALID_PERCENT);
        }
    }

    @Override
    public DiscountType getType() {
        return DiscountType.PERCENT;
    }

    @Override
    public int discount(int originalPrice) {
        int discountedAmount = calculateDiscountedAmount(originalPrice);
        return originalPrice - discountedAmount;
    }

    private int calculateDiscountedAmount(int originalPrice) {
        BigDecimal percent = BigDecimal.valueOf(getAmount()).divide(BigDecimal.valueOf(MAX_PERCENT));
        BigDecimal discountedAmount = percent.multiply(BigDecimal.valueOf(originalPrice));
        return discountedAmount.intValue();
    }
}
