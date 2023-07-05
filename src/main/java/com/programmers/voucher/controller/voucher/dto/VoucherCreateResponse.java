package com.programmers.voucher.controller.voucher.dto;

import com.programmers.voucher.domain.Voucher;

import java.util.UUID;

public record VoucherCreateResponse(
        UUID voucherId
) {
    public static VoucherCreateResponse from(Voucher voucher) {
        return new VoucherCreateResponse(voucher.getVoucherId());
    }
}
