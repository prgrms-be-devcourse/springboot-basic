package com.prgrms.voucher.model.discount;

import com.prgrms.common.exception.NegativeArgumentException;
import com.prgrms.order.model.Price;
import java.util.Objects;

public abstract class Discount {

    private final double discountAmount;

    public Discount(double discountAmount) {
        validPositiveDiscount(discountAmount);
        validLimit(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validPositiveDiscount(double value) {
        if (value < 0) {
            throw new NegativeArgumentException("할인 인자 : " + value);
        }
    }

    protected abstract void validLimit(double value);

    public double getDiscountAmount() {
        return discountAmount;
    }

    public abstract Price sale(Price originalPrice);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Discount discount = (Discount) o;
        return Double.compare(discount.discountAmount, discountAmount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount);
    }

}
