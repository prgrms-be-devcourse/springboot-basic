package com.prgmrs.voucher.model.strategy;

import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.model.vo.Percent;

public record PercentDiscountStrategy(Percent percent) implements DiscountStrategy {
    public PercentDiscountStrategy {
        if (percent.value() <= 0 || percent.value() > 100) {
            throw new IllegalArgumentException("percent must be between 1-100");
        }
    }

    @Override
    public DiscountValue discount(DiscountValue beforeDiscount) {
        return new DiscountValue((beforeDiscount.value() * percent.value()) / 100);
    }
}
