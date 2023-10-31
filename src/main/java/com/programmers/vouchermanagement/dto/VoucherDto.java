package com.programmers.vouchermanagement.dto;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherDto(UUID id,
                         String name,
                         float discountAmount,
                         LocalDateTime createdAt,
                         VoucherType voucherType) {
    public static VoucherDto of(String voucherName, float discountAmount, VoucherType voucherType) {
        return new VoucherDto(null, voucherName, discountAmount, null, voucherType);
    }

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(
                voucher.getId(),
                voucher.getName(),
                voucher.getDiscountAmount(),
                voucher.getCreatedAt(),
                voucher.getVoucherType());
    }
}
