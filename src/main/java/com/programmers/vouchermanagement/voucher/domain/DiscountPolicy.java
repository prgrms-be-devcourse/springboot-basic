package com.programmers.vouchermanagement.voucher.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class DiscountPolicy {

    private final int amount;

    DiscountPolicy(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    abstract void validateAmount(int amount);

    public int getAmount() {
        return amount;
    }

    public abstract DiscountType getType();

    public abstract int discount(int originalPrice);
}
