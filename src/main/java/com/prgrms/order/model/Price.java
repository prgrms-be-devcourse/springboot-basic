package com.prgrms.order.model;

import com.prgrms.common.message.ErrorMessage;
import com.prgrms.exception.NegativeArgumentException;

public record Price(double cost) {

    public Price {
        validPositiveDiscount(cost);
    }

    private void validPositiveDiscount(double cost) {
        if (cost < 0) {
            throw new NegativeArgumentException(ErrorMessage.NEGATIVE_ARGUMENT.getMessage());
        }
    }

    public Price getSaledPrice(double discountAmount) {
        return new Price(cost - discountAmount);
    }

}
