package com.prgmrs.voucher.model.strategy;

import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.DiscountValue;

public record FixedAmountDiscountStrategy(Amount amount) implements DiscountStrategy {
    public FixedAmountDiscountStrategy {
        if (amount.value() < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public DiscountValue discount(DiscountValue beforeDiscount) {
        if (amount.value() > beforeDiscount.value()) {
            return new DiscountValue(0);
        }
        return new DiscountValue(beforeDiscount.value() - amount.value());
    }
}