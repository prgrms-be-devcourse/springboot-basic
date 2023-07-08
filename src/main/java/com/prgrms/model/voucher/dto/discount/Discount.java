package com.prgrms.model.voucher.dto.discount;

import com.prgrms.view.message.ErrorMessage;

public abstract class Discount {
    private final double value;

    public Discount(double value) {
        validPositiveDiscount(value < 0);
        validLimit(value);
        this.value = value;
    }

    private void validPositiveDiscount(boolean expression) {
        if (expression) {
            throw new IllegalArgumentException(ErrorMessage.Negative_ARGUMENT.getMessage());
        }
    }

    protected abstract void validLimit(double value) ;

    public double getValue() {
        return value;
    }
}
