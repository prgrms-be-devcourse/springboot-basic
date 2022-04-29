package com.waterfogsw.voucher.voucher.dto;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

public record ResponseVoucherDto(
        Long id,
        VoucherType type,
        int value
) {

    public ResponseVoucherDto {
        validate(type, value);
    }

    public static ResponseVoucherDto of(Voucher voucher) {
        return new ResponseVoucherDto(voucher.getId(), voucher.getType(), voucher.getValue());
    }

    private static void validate(VoucherType type, int value) {
        if (type == null || value == 0) {
            throw new IllegalArgumentException();
        }
    }
}
