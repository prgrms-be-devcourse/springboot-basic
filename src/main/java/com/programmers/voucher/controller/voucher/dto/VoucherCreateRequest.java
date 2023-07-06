package com.programmers.voucher.controller.voucher.dto;

import com.programmers.voucher.entity.voucher.VoucherType;

public record VoucherCreateRequest(
        VoucherType voucherType,
        int discountAmount
) {
    public static VoucherCreateRequest of(VoucherType voucherType, int discountAmount) {
        return new VoucherCreateRequest(voucherType, discountAmount);
    }
}
