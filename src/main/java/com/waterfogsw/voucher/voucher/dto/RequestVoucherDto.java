package com.waterfogsw.voucher.voucher.dto;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

public record RequestVoucherDto(
        VoucherType type,
        int value
) {

    public RequestVoucherDto(VoucherType type, int value) {
        validate(type, value);
        this.type = type;
        this.value = value;
    }

    public Voucher toDomain() {
        return Voucher.of(type(), value());
    }

    private static void validate(VoucherType type, int value) {
        if (type == null || value == 0) {
            throw new IllegalArgumentException();
        }
    }
}
