package com.prgrms.model.voucher.dto.discount;

import com.prgrms.view.message.ErrorMessage;

public abstract class Discount {
    private final double value;

    public Discount(double value) {
        validPositiveDiscount(value);
        validLimit(value);
        this.value = value;
    }

    private void validPositiveDiscount(double value) {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMessage.NEGATIVE_ARGUMENT.getMessage());
        }
    }

    protected abstract void validLimit(double value);

    public double getValue() {
        return value;
    }
}
