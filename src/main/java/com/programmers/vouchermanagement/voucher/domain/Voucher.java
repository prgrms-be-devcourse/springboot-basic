package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class Voucher {

    private final UUID id;
    private final DiscountPolicy discountPolicy;
    private final DiscountType discountType;

    public Voucher(DiscountPolicy discountPolicy, DiscountType discountType) {
        this.id = UUID.randomUUID();
        this.discountPolicy = discountPolicy;
        this.discountType = discountType;
    }

    public UUID getId() {
        return id;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public int discount(int originalPrice) {
        return discountPolicy.discount(originalPrice);
    }
}
