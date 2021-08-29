package com.programmers.voucher.entity.voucher;

import java.io.Serializable;
import java.util.function.BinaryOperator;

public class DiscountPolicy implements Serializable {

    public enum Type {
        FIXED((price, discount) -> Math.max(price - discount, 0)),
        PERCENTAGE((price, discount) -> Math.min(price * (100 - discount) / 100, price));

        private BinaryOperator<Integer> discountPolicy;

        Type(BinaryOperator<Integer> discountPolicy) {
            this.discountPolicy = discountPolicy;
        }

        public BinaryOperator<Integer> getDiscountPolicy() {
            return discountPolicy;
        }

        public static Type of(String input) {
            try {
                return Type.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return Type.FIXED;
            }
        }
    }

    private int amount;
    private Type type;

    public DiscountPolicy(int amount, Type type) {
        this.amount = amount;
        this.type = type;
    }

    public int discount(int price) {
        return type.discountPolicy.apply(price, amount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
