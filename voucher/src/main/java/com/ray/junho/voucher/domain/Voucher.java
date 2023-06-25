package com.ray.junho.voucher.domain;

public interface Voucher {
    Currency discount(Currency beforeDiscount);
}
