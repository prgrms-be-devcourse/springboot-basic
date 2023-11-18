package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherResponseDto {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Long discount;
    private final LocalDateTime createdAt;

    public VoucherResponseDto(UUID voucherId, VoucherType voucherType, Long discount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = discount;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Long getDiscount() {
        return discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
