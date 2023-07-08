package com.prgmrs.voucher.model.strategy;

import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.model.vo.Percent;

public class PercentDiscountStrategy implements DiscountStrategy {
    private final Percent percent;

    public PercentDiscountStrategy(Percent percent) {
        if (percent.getValue() <= 0 || percent.getValue() > 100) {
            throw new IllegalArgumentException("percent must be between 1-100");
        }
        this.percent = percent;
    }

    @Override
    public DiscountValue discount(DiscountValue beforeDiscount) {
        return new DiscountValue((beforeDiscount.getValue() * percent.getValue()) / 100);
    }

    public Percent getPercent() {
        return percent;
    }
}
