package com.prgrms.order.model;

import com.prgrms.common.exception.NegativeArgumentException;

public record Price(double cost) {

    public Price {
        validPositiveDiscount(cost);
    }

    private void validPositiveDiscount(double cost) {
        if (cost < 0) {
            throw new NegativeArgumentException("가격 : " + cost);
        }
    }

    public Price getSaledPrice(double discountAmount) {
        return new Price(cost - discountAmount);
    }

}
