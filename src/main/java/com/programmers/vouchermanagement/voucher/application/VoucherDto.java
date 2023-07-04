package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.DiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;

public record VoucherDto(DiscountType discountType, int amount) {

    public static VoucherDto toDto(Voucher voucher) {
        DiscountType discountType = voucher.getDiscountType();
        DiscountPolicy discountPolicy = voucher.getDiscountPolicy();
        return new VoucherDto(discountType, discountPolicy.getAmount());
    }
}
