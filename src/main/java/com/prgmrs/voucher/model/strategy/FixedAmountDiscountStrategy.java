package com.prgmrs.voucher.model.strategy;

import com.prgmrs.voucher.model.wrapper.Amount;
import com.prgmrs.voucher.model.wrapper.DiscountValue;

public record FixedAmountDiscountStrategy(Amount amount) implements DiscountStrategy {

    @Override
    public DiscountValue discount(DiscountValue beforeDiscount) {
        if (amount.value() > beforeDiscount.value()) {
            return new DiscountValue(0);
        }
        return new DiscountValue(beforeDiscount.value() - amount.value());
    }
}