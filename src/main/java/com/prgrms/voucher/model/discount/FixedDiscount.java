package com.prgrms.voucher.model.discount;

import com.prgrms.voucher.exception.AmountLimitException;
import com.prgrms.order.model.Price;

public class FixedDiscount extends Discount {

    private final int limit = 10_000;

    public FixedDiscount(double value) {
        super(value);
    }

    @Override
    protected void validLimit(double value) {
        if (value > limit) {
            throw new AmountLimitException("입력한 금액 : "+value);
        }
    }

    @Override
    public Price sale(Price originalPrice) {
        double amount = getDiscountAmount();
        return originalPrice.getSaledPrice(amount);
    }
}
