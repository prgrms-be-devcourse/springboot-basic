package com.programmers.vouchermanagement.voucher.domain;

public interface DiscountPolicy {

    int getAmount();

    DiscountType getType();

    int discount(int originalPrice);
}
