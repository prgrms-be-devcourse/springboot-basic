package com.programmers.voucher.domain.voucher.dto;

import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VoucherCreateRequest(
        @NotNull VoucherType voucherType,
        @Positive int discountAmount
) {
    public static VoucherCreateRequest of(VoucherType voucherType, int discountAmount) {
        return new VoucherCreateRequest(voucherType, discountAmount);
    }

    public Voucher toEntity() {
        return Voucher.create(voucherType, discountAmount);
    }
}
