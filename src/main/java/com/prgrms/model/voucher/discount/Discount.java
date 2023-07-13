package com.prgrms.model.voucher.discount;

import com.prgrms.model.order.Price;
import com.prgrms.presentation.message.ErrorMessage;

public abstract class Discount {
    private final double discountAmount;

    public Discount(double discountAmount) {
        validPositiveDiscount(discountAmount);
        validLimit(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validPositiveDiscount(double value) {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMessage.NEGATIVE_ARGUMENT.getMessage());
        }
    }

    protected abstract void validLimit(double value);

    public double getDiscountAmount() {
        return discountAmount;
    }

    public abstract Price sale(Price originalPrice);
}
