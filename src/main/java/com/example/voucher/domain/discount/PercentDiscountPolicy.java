package com.example.voucher.domain.discount;

import com.example.voucher.domain.Voucher;

public class PercentDiscountPolicy implements DiscountPolicy {
    private final double percent;

    public PercentDiscountPolicy(double percent) {
        this.percent = percent;
    }

    @Override
    public double applyDiscount(Voucher voucher) {
        double originalPrice = voucher.getAmount();
        double discountAmount = originalPrice * percent / 100;
        double discountedPrice = originalPrice - discountAmount;
        voucher.setAmount(discountedPrice);
        return discountAmount;
    }
}