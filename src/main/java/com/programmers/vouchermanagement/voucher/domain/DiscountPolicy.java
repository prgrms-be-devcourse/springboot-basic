package com.programmers.vouchermanagement.voucher.domain;

public interface DiscountPolicy {

    int getAmount();

    int discount(int originalPrice);
}
