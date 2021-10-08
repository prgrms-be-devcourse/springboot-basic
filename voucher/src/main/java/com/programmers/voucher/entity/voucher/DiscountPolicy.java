package com.programmers.voucher.entity.voucher;

import java.io.Serializable;
import java.util.Objects;

public class DiscountPolicy implements Serializable {

    private int amount;
    private DiscountType type;

    public DiscountPolicy(DiscountPolicy discountPolicy) {
        this.amount = discountPolicy.amount;
        this.type = discountPolicy.type;
    }

    public DiscountPolicy(int amount, DiscountType type) {
        this.type = type;
        this.amount = type.constraint(amount);
    }

    public int discount(int price) {
        return type.discount(price, amount);
    }

    public int getAmount() {
        return amount;
    }

    public void updateAmount(int amount) {
        amount = this.type.constraint(amount);
        this.amount = amount;
    }

    public DiscountType getType() {
        return type;
    }

    public void updateType(DiscountType type) {
        this.type = type;
        this.amount = type.constraint(amount);
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
