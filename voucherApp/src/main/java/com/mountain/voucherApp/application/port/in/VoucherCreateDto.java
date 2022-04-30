package com.mountain.voucherApp.application.port.in;

import com.mountain.voucherApp.shared.enums.DiscountPolicy;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherCreateDto that = (VoucherCreateDto) o;
        return discountPolicy == that.discountPolicy && Objects.equals(discountAmount, that.discountAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountPolicy, discountAmount);
    }
}
