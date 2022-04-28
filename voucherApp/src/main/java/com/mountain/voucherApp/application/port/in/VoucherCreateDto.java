package com.mountain.voucherApp.application.port.in;

import com.mountain.voucherApp.shared.enums.DiscountPolicy;

public class VoucherCreateDto {
    private DiscountPolicy discountPolicy;
    private Long discountAmount;

    public VoucherCreateDto(DiscountPolicy discountPolicy, Long discountAmount) {
        this.discountPolicy = discountPolicy;
        this.discountAmount = discountAmount;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }
}
