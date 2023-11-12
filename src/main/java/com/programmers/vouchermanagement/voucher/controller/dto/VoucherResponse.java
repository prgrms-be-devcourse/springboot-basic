package com.programmers.vouchermanagement.voucher.controller.dto;

import com.programmers.vouchermanagement.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherResponse(UUID id, LocalDateTime createdAt, String typeName, long discountValue) {
    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getCreatedAt(), voucher.getTypeName(), voucher.getDiscountValue());
    }
}
