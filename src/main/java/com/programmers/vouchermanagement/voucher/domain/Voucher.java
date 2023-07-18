package com.programmers.vouchermanagement.voucher.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Voucher {

    private UUID id;
    private DiscountPolicy discountPolicy;

    public static Voucher from(DiscountPolicy discountPolicy) {
        UUID id = UUID.randomUUID();
        return new Voucher(id, discountPolicy);
    }

    public static Voucher of(UUID id, DiscountPolicy discountPolicy) {
        return new Voucher(id, discountPolicy);
    }

    public int discount(int originalPrice) {
        return discountPolicy.discount(originalPrice);
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
