package org.prgrms.assignment.voucher.dto;

import org.prgrms.assignment.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherServiceRequestDTO(
    UUID voucherId,
    VoucherType voucherType,
    long benefit,
    LocalDateTime createdAt,
    LocalDateTime expireDate
){
    public static VoucherServiceRequestDTO of(VoucherCreateRequestDTO voucherViewRequestDTO) {
        return new VoucherServiceRequestDTO(
            UUID.randomUUID(),
            voucherViewRequestDTO.voucherType(),
            voucherViewRequestDTO.benefit(),
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(voucherViewRequestDTO.durationDate())
        );
    }
}
