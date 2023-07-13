package com.example.voucher.domain.discount;

import com.example.voucher.domain.Voucher;

public interface DiscountPolicy {
    double applyDiscount(Voucher voucher);
}
