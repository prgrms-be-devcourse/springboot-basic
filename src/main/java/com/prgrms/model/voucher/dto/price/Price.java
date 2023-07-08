package com.prgrms.model.voucher.dto.price;

import com.prgrms.view.message.ErrorMessage;

public class Price {
    private final double value;

    public Price(double value) {
        validPositivePrice(value < 0);
        this.value = value;
    }

    private void validPositivePrice(boolean expression) {
        if (expression) {
            throw new IllegalArgumentException(ErrorMessage.Negative_ARGUMENT.getMessage());
        }
    }
    public double getValue() {
        return value;
    }
}
