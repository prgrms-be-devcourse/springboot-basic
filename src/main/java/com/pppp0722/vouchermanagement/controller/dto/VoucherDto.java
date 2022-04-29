package com.pppp0722.vouchermanagement.controller.dto;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherDto(
    UUID voucherId,
    VoucherType type,
    long amount,
    LocalDateTime createdAt,
    UUID memberId
) {

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getType(), voucher.getAmount(),
            voucher.getCreatedAt(), voucher.getMemberId());
    }
}
