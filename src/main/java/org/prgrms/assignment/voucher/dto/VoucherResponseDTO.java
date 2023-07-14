package org.prgrms.assignment.voucher.dto;

import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherResponseDTO(
    UUID voucherId,
    VoucherType voucherType,
    long benefit,
    LocalDateTime createdAt,
    LocalDateTime expireDate
) {

    public static VoucherResponseDTO of(VoucherEntity voucherEntity) {
        return new VoucherResponseDTO(
            voucherEntity.voucherId(),
            voucherEntity.voucherType(),
            voucherEntity.benefit(),
            voucherEntity.createdAt(),
            voucherEntity.expireDate()
        );
    }
}
