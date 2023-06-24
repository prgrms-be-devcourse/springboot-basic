package com.programmers.voucher.domain;

import com.programmers.voucher.request.VoucherCreationRequest;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher createVoucher(VoucherCreationRequest voucherCreationRequest) {
        VoucherType voucherType = VoucherType.valueOf(voucherCreationRequest.getType());
        return switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), voucherCreationRequest.getAmount());

            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(), voucherCreationRequest.getAmount());
        };
    }

}
