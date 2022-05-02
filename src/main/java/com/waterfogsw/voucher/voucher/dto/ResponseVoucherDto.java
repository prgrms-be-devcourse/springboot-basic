package com.waterfogsw.voucher.voucher.dto;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

import java.time.LocalDateTime;

public record ResponseVoucherDto(
        Long id,
        VoucherType type,
        int value,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public ResponseVoucherDto {
        validate(type, value);
    }

    public static ResponseVoucherDto from(Voucher voucher) {
        return new ResponseVoucherDto(voucher.getId(),
                voucher.getType(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                voucher.getUpdatedAt());
    }

    private static void validate(VoucherType type, int value) {
        if (type == null || value == 0) {
            throw new IllegalArgumentException();
        }
    }
}
