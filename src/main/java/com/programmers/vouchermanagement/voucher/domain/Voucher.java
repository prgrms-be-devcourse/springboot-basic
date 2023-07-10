package com.programmers.vouchermanagement.voucher.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Voucher {

    private final UUID id;
    private DiscountPolicy discountPolicy;

    public Voucher(DiscountPolicy discountPolicy) {
        this.id = UUID.randomUUID();
        this.discountPolicy = discountPolicy;
    }

    public int discount(int originalPrice) {
        return discountPolicy.discount(originalPrice);
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
