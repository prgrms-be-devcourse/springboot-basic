package com.programmers.voucher.controller.voucher.dto;

import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherType;

public record VoucherCreateRequest(
        VoucherType voucherType,
        long discountAmount
) {
    public static VoucherCreateRequest of(VoucherType voucherType, DiscountAmount discountAmount) {
        return new VoucherCreateRequest(voucherType, discountAmount.getAmount());
    }
}
