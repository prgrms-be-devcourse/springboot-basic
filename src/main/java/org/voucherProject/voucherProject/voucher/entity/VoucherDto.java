package org.voucherProject.voucherProject.voucher.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class VoucherDto {

    private UUID voucherId;

    private long amount;

    private VoucherType voucherType;

    private VoucherStatus voucherStatus;

    private LocalDateTime createdAt;

    private UUID customerId;

}
