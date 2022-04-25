package com.waterfogsw.voucher.voucher.dto;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

public record ResponseVoucherDto(
        VoucherType type,
        int value
) {

    public ResponseVoucherDto(VoucherType type, int value) {
        validate(type, value);
        this.type = type;
        this.value = value;
    }

    public static ResponseVoucherDto of(Voucher voucher) {
        return new ResponseVoucherDto(voucher.getType(), voucher.getValue());
    }

    private static void validate(VoucherType type, int value) {
        if (type == null || value == 0) {
            throw new IllegalArgumentException();
        }
    }
}
