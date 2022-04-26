package org.prgrms.part1.engine.controller.dto;

import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.VoucherType;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class VoucherResponseDto {
    private UUID voucherId;
    private Integer value;
    private VoucherType voucherType;
    private LocalDateTime createdAt;
    private Optional<UUID> customerId;

    public VoucherResponseDto(Voucher entity) {
        this.voucherId = entity.getVoucherId();
        this.value = entity.getValue();
        this.voucherType = entity.getVoucherType();
        this.createdAt = entity.getCreatedAt();
        this.customerId = entity.getCustomerId();
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

    public Optional<UUID> getCustomerId() {
        return customerId;
    }
}
