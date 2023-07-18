package com.programmers.vouchermanagement.voucher.dto.request;

import com.programmers.vouchermanagement.voucher.domain.DiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record VoucherUpdateRequest(DiscountType type, @Min(1) int amount) {
    public Voucher toEntity(UUID id) {
        DiscountPolicy discountPolicy = this.type.createDiscountPolicy(this.amount);
        return Voucher.of(id, discountPolicy);
    }
}
