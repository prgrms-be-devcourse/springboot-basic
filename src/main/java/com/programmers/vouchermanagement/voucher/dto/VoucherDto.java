package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherDto(UUID id, long discountValue, String voucherTypeName, boolean isPercent,
                         LocalDateTime createdAt) {
    public static VoucherDto from(Voucher voucher) {
        VoucherType voucherType = voucher.voucherType();
        return new VoucherDto(voucher.voucherId(), voucher.discountValue(), voucherType.displayTypeName(), voucherType.isPercent(), voucher.createdAt());
    }
}
