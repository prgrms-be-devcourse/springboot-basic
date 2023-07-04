package com.programmers.vouchermanagement.voucher.domain;

public abstract class DiscountPolicy {

    abstract int discount(int originalPrice);
}
