package com.prgmrs.voucher.model.strategy;

import com.prgmrs.voucher.model.wrapper.DiscountValue;
import com.prgmrs.voucher.model.wrapper.Percent;

public record PercentDiscountStrategy(Percent percent) implements DiscountStrategy {

    @Override
    public DiscountValue discount(DiscountValue beforeDiscount) {
        return new DiscountValue((beforeDiscount.value() * percent.value()) / 100);
    }
}
