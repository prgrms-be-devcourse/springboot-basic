package com.programmers.voucher.domain;

import com.programmers.voucher.dto.request.VoucherCreationRequest;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher createVoucher(VoucherCreationRequest voucherCreationRequest) {
        VoucherType voucherType = VoucherType.valueOf(voucherCreationRequest.voucherType());
        return switch (voucherType) {
            case FIXED -> FixedAmountVoucher.of(UUID.randomUUID(), voucherCreationRequest.discountAmount());
            case PERCENT -> PercentDiscountVoucher.of(UUID.randomUUID(), voucherCreationRequest.discountAmount());
        };
    }

}
