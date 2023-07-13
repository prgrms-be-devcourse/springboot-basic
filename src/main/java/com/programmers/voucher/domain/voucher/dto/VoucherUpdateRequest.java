package com.programmers.voucher.domain.voucher.dto;

import com.programmers.voucher.domain.voucher.entity.VoucherType;

public record VoucherUpdateRequest(
        VoucherType voucherType,
        int discountAmount
) {
    public static VoucherUpdateRequest of(VoucherType voucherType, int discountAmount) {
        return new VoucherUpdateRequest(voucherType, discountAmount);
    }
}
