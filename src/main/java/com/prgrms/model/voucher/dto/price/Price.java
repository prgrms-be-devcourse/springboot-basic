package com.prgrms.model.voucher.dto.price;

import com.prgrms.view.message.ErrorMessage;

public class Price {
    private final double value;

    public Price(double value) {
        validPositivePrice(value);
        this.value = value;
    }

    private void validPositivePrice(double value) {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMessage.NEGATIVE_ARGUMENT.getMessage());
        }
    }

    public double getValue() {
        return value;
    }
}
