package com.example.voucher.domain.discount;

import com.example.voucher.domain.Voucher;

public class FixedAmountDiscountPolicy implements DiscountPolicy {
    private final double fixedAmount;

    public FixedAmountDiscountPolicy(double fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public double applyDiscount(Voucher voucher) {
        double originalPrice = voucher.getAmount();
        return originalPrice - fixedAmount;
    }
}
