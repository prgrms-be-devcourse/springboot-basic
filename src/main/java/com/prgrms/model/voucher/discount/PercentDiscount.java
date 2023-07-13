package com.prgrms.model.voucher.discount;

import com.prgrms.model.order.Price;

public class PercentDiscount extends Discount {

    private final double PERCENT = 100;
    private final int limit = 100;
    private final String limitException = "할인율은" + limit + "%를 넘을 수 없습니다.";

    public PercentDiscount(double value) {
        super(value);
    }

    @Override
    protected void validLimit(double value) {
        if (value >= limit) {
            throw new IllegalArgumentException(limitException);
        }
    }

    @Override
    public Price sale(Price originalPrice) {
        double amount = originalPrice.cost() * getDiscountAmount() / PERCENT;
        return originalPrice.getSaledPrice(amount);
    }
}
