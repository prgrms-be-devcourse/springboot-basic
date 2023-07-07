package com.programmers.vouchermanagement.voucher.domain;

import java.util.Objects;
import java.util.UUID;

public class Voucher {

    private final UUID id;
    private DiscountPolicy discountPolicy;

    public Voucher(DiscountPolicy discountPolicy) {
        this.id = UUID.randomUUID();
        this.discountPolicy = discountPolicy;
    }

    public Voucher(UUID id, DiscountPolicy discountPolicy) {
        this.id = id;
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

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Voucher voucher = (Voucher) o;

        if (!Objects.equals(id, voucher.id)) return false;
        return Objects.equals(discountPolicy, voucher.discountPolicy);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (discountPolicy != null ? discountPolicy.hashCode() : 0);
        return result;
    }
}
