package com.programmers.voucher.domain.voucher.dto;

import com.programmers.voucher.domain.voucher.entity.VoucherType;

public record VoucherUpdateRequest(
        VoucherType voucherType,
        int discountAmount
) {
    public static VoucherUpdateRequest of(String type, int discountAmount) {
        return new VoucherUpdateRequest(VoucherType.valueOf(type), discountAmount);
    }
}
