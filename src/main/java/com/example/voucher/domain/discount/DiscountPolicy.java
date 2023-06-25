package com.example.voucher.domain.discount;

import com.example.voucher.domain.Voucher;

public interface DiscountPolicy {
    void applyDiscount(Voucher voucher);
}
