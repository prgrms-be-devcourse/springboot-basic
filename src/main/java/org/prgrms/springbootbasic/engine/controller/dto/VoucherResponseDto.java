package org.prgrms.springbootbasic.engine.controller.dto;

import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherResponseDto {
    private UUID voucherId;
    private Integer value;
    private VoucherType voucherType;
    private LocalDateTime createdAt;
    private UUID customerId;

    public VoucherResponseDto(Voucher entity) {
        this.voucherId = entity.getVoucherId();
        this.value = entity.getValue();
        this.voucherType = entity.getVoucherType();
        this.createdAt = entity.getCreatedAt();
        this.customerId = entity.getCustomerId().isEmpty() ? null : entity.getCustomerId().get();
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Integer getValue() {
        return value;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
