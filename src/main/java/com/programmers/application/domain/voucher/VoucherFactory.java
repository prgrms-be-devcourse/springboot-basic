package com.programmers.application.domain.voucher;

import com.programmers.application.dto.request.VoucherCreationRequest;

import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory() {}

    public static Voucher createVoucher(VoucherCreationRequest voucherCreationRequest) {
        String requestVoucherType = voucherCreationRequest.voucherType().toUpperCase();
        VoucherType voucherType = VoucherType.valueOf(requestVoucherType);
        return switch (voucherType) {
            case FIXED -> FixedAmountVoucher.of(UUID.randomUUID(), voucherCreationRequest.discountAmount());
            case PERCENT -> PercentDiscountVoucher.of(UUID.randomUUID(), voucherCreationRequest.discountAmount());
        };
    }
}
