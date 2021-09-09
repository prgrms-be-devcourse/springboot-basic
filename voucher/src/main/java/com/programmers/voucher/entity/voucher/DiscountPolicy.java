package com.programmers.voucher.entity.voucher;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class DiscountPolicy implements Serializable {

    public enum Type {
        FIXED(
                (price, discount) -> Math.max(price - discount, 0),
                input -> Math.max(input, 0)),
        PERCENTAGE(
                (price, discount) -> Math.min(price * (100 - discount) / 100, price),
                input -> Math.min(Math.max(input, 0), 100));

        BinaryOperator<Integer> operation;
        UnaryOperator<Integer> constraint;

        public BinaryOperator<Integer> getOperation() {
            return operation;
        }

        public UnaryOperator<Integer> getConstraint() {
            return constraint;
        }

        public int constraint(int original) {
            return this.constraint.apply(original);
        }

        Type(BinaryOperator<Integer> operation, UnaryOperator<Integer> constraint) {
            this.operation = operation;
            this.constraint = constraint;
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
        return type.operation.apply(price, amount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        amount = this.type.constraint.apply(amount);
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, type);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof DiscountPolicy)) return false;
        DiscountPolicy other = (DiscountPolicy) obj;
        return this.amount == other.amount &&
                this.type.equals(other.type);
    }
}
