package com.programmers.voucher.domain;

public interface Discount {
    long discount(long beforeDiscount);

    long getValue();
}
