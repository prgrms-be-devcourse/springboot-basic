package com.waterfogsw.voucher.voucher.dto;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

public record VoucherDto(
        VoucherType type,
        int value
) {
    public static Voucher toDomain(VoucherDto dto) {
        return Voucher.of(dto.type(), dto.value());
    }

    public static VoucherDto of(Voucher voucher) {
        return new VoucherDto(voucher.getType(), voucher.getValue());
    }
}
