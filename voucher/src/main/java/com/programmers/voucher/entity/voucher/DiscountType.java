package com.programmers.voucher.entity.voucher;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public enum DiscountType {
    FIXED(
            (price, discountAmount) -> Math.max(price - discountAmount, 0),
            input -> Math.max(input, 0)),
    PERCENTAGE(
            (price, discountAmount) -> Math.min(price * (100 - discountAmount) / 100, price),
            input -> Math.min(Math.max(input, 0), 100));

    private final BinaryOperator<Integer> discount;
    private final UnaryOperator<Integer> constraint;

    public int discount(int original, int amount) {
        return this.discount.apply(original, amount);
    }

    public int constraint(int original) {
        return this.constraint.apply(original);
    }

    DiscountType(BinaryOperator<Integer> discount, UnaryOperator<Integer> constraint) {
        this.discount = discount;
        this.constraint = constraint;
    }

    public static DiscountType of(String input) {
        try {
            return DiscountType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return DiscountType.FIXED;
        }
    }
}
