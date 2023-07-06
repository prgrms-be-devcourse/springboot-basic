package com.programmers.voucher.domain.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.programmers.voucher.constant.ErrorMessage.INVALID_DISCOUNT_AMOUNT;
import static com.programmers.voucher.constant.ErrorMessage.INVALID_DISCOUNT_PERCENT;

public class DiscountAmount {
    private static final Logger logger = LoggerFactory.getLogger(DiscountAmount.class);
    private final VoucherType voucherType;
    private final int amount;

    public DiscountAmount(VoucherType voucherType, int amount) {
        this.voucherType = voucherType;
        this.amount = amount;
        validatePositive();
        validatePercent();
    }

    private void validatePositive() {
        if (amount <= 0) {
            logger.error("{} => {}", INVALID_DISCOUNT_AMOUNT, amount);
            throw new IllegalArgumentException(INVALID_DISCOUNT_AMOUNT);
        }
    }

    private void validatePercent() {
        if (voucherType == VoucherType.PERCENT && amount > 100) {
            logger.error("{} => {}", INVALID_DISCOUNT_PERCENT, amount);
            throw new IllegalArgumentException(INVALID_DISCOUNT_PERCENT);
        }
    }

    public int getAmount() {
        return amount;
    }
}
