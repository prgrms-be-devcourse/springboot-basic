package com.prgrms.order.model;

import com.prgrms.common.message.ErrorMessage;

public record Price(double cost) {

    public Price {
        validPositiveDiscount(cost);
    }

    private void validPositiveDiscount(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException(ErrorMessage.NEGATIVE_ARGUMENT.getMessage());
        }
    }

    public Price getSaledPrice(double discountAmount) {
        return new Price(cost - discountAmount);
    }
}
