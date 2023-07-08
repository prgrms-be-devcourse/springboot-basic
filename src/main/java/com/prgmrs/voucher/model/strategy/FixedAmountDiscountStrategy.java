package com.prgmrs.voucher.model.strategy;

import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.DiscountValue;

public class FixedAmountDiscountStrategy implements DiscountStrategy {
    private final Amount amount;

    public FixedAmountDiscountStrategy(Amount amount) {
        if (amount.getValue() < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        this.amount = amount;
    }

    @Override
    public DiscountValue discount(DiscountValue beforeDiscount) {
        if (amount.getValue() > beforeDiscount.getValue()) {
            return new DiscountValue(0);
        }
        return new DiscountValue(beforeDiscount.getValue() - amount.getValue());
    }

    public Amount getAmount() {
        return amount;
    }
}