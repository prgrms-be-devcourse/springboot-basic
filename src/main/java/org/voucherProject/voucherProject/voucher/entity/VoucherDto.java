package org.voucherProject.voucherProject.voucher.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDto {

    private UUID voucherId;

    private long amount;

    private VoucherType voucherType;

    private VoucherStatus voucherStatus;

    private LocalDateTime createdAt;

    private UUID customerId;

    public static VoucherDto of(Voucher voucher) {
        return VoucherDto.builder()
                .voucherId(voucher.getVoucherId())
                .amount(voucher.getHowMuch())
                .voucherType(voucher.getVoucherType())
                .voucherStatus(voucher.getVoucherStatus())
                .createdAt(voucher.getCreatedAt())
                .customerId(voucher.getCustomerId())
                .build();
    }

}
