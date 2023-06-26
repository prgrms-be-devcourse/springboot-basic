package com.programmers.voucher.view.dto;

import com.programmers.voucher.constant.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscountAmount {
    private final Logger logger = LoggerFactory.getLogger(DiscountAmount.class);
    private final long amount;

    public DiscountAmount(VoucherType voucherType, long amount) {
        this.amount = amount;
        validatePositive();
        validatePercent(voucherType);
    }

    private void validatePositive() {
        if (amount < 0) {
            logger.error("{} => {}", ErrorMessage.INVALID_DISCOUNT_AMOUNT, amount);
            throw new IllegalArgumentException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        }
    }

    private void validatePercent(VoucherType voucherType) {
        if (voucherType == VoucherType.PERCENT_DISCOUNT && amount >= 100) {
            logger.error("{} => {}", ErrorMessage.INVALID_DISCOUNT_PERCENT, amount);
            throw new IllegalArgumentException(ErrorMessage.INVALID_DISCOUNT_PERCENT);
        }
    }

    public long getAmount() {
        return amount;
    }
}
