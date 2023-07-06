package com.programmers.voucher.controller.voucher.dto;

import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.entity.voucher.VoucherType;
import com.programmers.voucher.view.dto.DiscountAmount;

public record VoucherCreateRequest(
        VoucherType voucherType,
        int discountAmount
) {
    public static VoucherCreateRequest of(VoucherType voucherType, DiscountAmount discountAmount) {
        return new VoucherCreateRequest(voucherType, discountAmount.getAmount());
    }

    public Voucher toEntity() {
        return Voucher.create(voucherType, discountAmount);
    }
}
