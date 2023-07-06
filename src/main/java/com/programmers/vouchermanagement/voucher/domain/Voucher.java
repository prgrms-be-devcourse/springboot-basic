package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class Voucher {

    private final UUID id;
    private final DiscountPolicy discountPolicy;

    public Voucher(DiscountPolicy discountPolicy) {
        this.id = UUID.randomUUID();
        this.discountPolicy = discountPolicy;
    }

    public UUID getId() {
        return id;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public int discount(int originalPrice) {
        return discountPolicy.discount(originalPrice);
    }
}
