package com.programmers.voucher.controller.voucher.dto;

import com.programmers.voucher.entity.voucher.VoucherType;

public record VoucherUpdateRequest(
        VoucherType voucherType,
        int discountAmount
) {
    public static VoucherUpdateRequest of(VoucherType voucherType, int discountAmount) {
        return new VoucherUpdateRequest(voucherType, discountAmount);
    }
}
