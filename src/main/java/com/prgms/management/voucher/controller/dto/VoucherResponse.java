package com.prgms.management.voucher.controller.dto;

import com.prgms.management.voucher.model.Voucher;

import java.sql.Timestamp;
import java.util.UUID;

public record VoucherResponse(
    UUID id,
    Timestamp createdAt,
    Integer figure,
    String type,
    String name
) {
    public static VoucherResponse of(Voucher voucher) {
        return new VoucherResponse(
            voucher.getId(),
            voucher.getCreatedAt(),
            voucher.getFigure(),
            voucher.getType().toString(),
            voucher.getName()
        );
    }
}
