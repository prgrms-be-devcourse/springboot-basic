package com.voucher.vouchermanagement.domain.voucher.dto;

import com.voucher.vouchermanagement.domain.voucher.model.Voucher;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {

    private final VoucherType voucherType;
    private final UUID id;
    private final long value;
    private final LocalDateTime createdAt;

    public VoucherDto(UUID id, long value, LocalDateTime createdAt, VoucherType voucherType) {
        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public UUID getId() {
        return id;
    }

    public long getValue() {
        return value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static VoucherDto of(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                VoucherType.getVoucherTypeByName(voucher.getClass().getSimpleName()));
    }

    @Override
    public String toString() {
        return "voucherType=" + voucherType.getTypeName() +
                ", id=" + id +
                ", value=" + value +
                ", createdAt=" + createdAt;
    }
}
