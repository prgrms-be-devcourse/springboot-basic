package com.waterfogsw.voucher.voucher.dto;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

public record VoucherDto(
        VoucherType type,
        int value
) {

    public Voucher toDomain() {
        return Voucher.of(type(), value());
    }

    public static VoucherDto of(Voucher voucher) {
        return new VoucherDto(voucher.getType(), voucher.getValue());
    }

    private static void validate(VoucherType type, int value) {
        if (type == null || value == 0) {
            throw new IllegalArgumentException();
        }
    }
}
