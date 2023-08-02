package com.prgrms.voucher.model.discount;

import com.prgrms.voucher.exception.PercentLimitException;
import com.prgrms.order.model.Price;

public class PercentDiscount extends Discount {

    private final double PERCENT = 100;
    private final int limit = 100;

    public PercentDiscount(double value) {
        super(value);
    }

    @Override
    protected void validLimit(double value) {
        if (value >= limit) {
            throw new PercentLimitException("입력한 할인율 : " + value);
        }
    }

    @Override
    public Price sale(Price originalPrice) {
        double amount = originalPrice.cost() * getDiscountAmount() / PERCENT;
        return originalPrice.getSaledPrice(amount);
    }

}

