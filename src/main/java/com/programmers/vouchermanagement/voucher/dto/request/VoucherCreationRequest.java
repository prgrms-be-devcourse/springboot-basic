package com.programmers.vouchermanagement.voucher.dto.request;

import com.programmers.vouchermanagement.voucher.domain.DiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import jakarta.validation.constraints.Min;

public record VoucherCreationRequest(DiscountType type, @Min(1) int amount) {
    public Voucher toEntity() {
        DiscountPolicy discountPolicy = this.type.createDiscountPolicy(this.amount);
        return Voucher.from(discountPolicy);
    }
}