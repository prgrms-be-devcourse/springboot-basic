package com.programmers.vouchermanagement.voucher.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class DiscountPolicy {

    private final int amount;

    DiscountPolicy(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    abstract void validateAmount(int amount);

    public abstract DiscountType getType();

    public abstract int discount(int originalPrice);
}
