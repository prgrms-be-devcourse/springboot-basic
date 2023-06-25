package com.dev.bootbasic.voucher.dto;

import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherType;

public record VoucherDetailsResponse(VoucherType voucherType, int discountAmount) {

    public static VoucherDetailsResponse create(Voucher voucher) {
        return new VoucherDetailsResponse(voucher.getVoucherType(), voucher.getDiscountAmount());
    }
}
