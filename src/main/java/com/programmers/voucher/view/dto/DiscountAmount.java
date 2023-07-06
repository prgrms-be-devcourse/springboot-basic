package com.programmers.voucher.view.dto;

import com.programmers.voucher.constant.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscountAmount {
    private static final Logger logger = LoggerFactory.getLogger(DiscountAmount.class);
    private final int amount;

    public DiscountAmount(int amount) {
        this.amount = amount;
        validatePositive();
    }

    private void validatePositive() {
        if (amount <= 0) {
            logger.error("{} => {}", ErrorMessage.INVALID_DISCOUNT_AMOUNT, amount);
            throw new IllegalArgumentException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        }
    }

    public int getAmount() {
        return amount;
    }
}
