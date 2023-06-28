package com.dev.bootbasic.voucher.dto;

import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherType;

import java.util.UUID;

public record VoucherDetailsResponse(UUID id, VoucherType voucherType, int discountAmount) {

    public static VoucherDetailsResponse from(Voucher voucher) {
        return new VoucherDetailsResponse(voucher.getId(), voucher.getVoucherType(), voucher.getDiscountAmount());
    }

}
