package org.programmer.kdtspringboot.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final Long discountPercent;
    private final String type;
    private final LocalDateTime createdAt;

    public VoucherDto(UUID voucherId, Long discountPercent, String type, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
        this.type = type;
        this.createdAt = createdAt;
    }

    public VoucherDto() {
        this.voucherId = null;
        this.discountPercent = null;
        this.type = null;
        this.createdAt = null;
    }

    public VoucherDto(Long discountPercent, String type) {
        this.voucherId = null;
        this.discountPercent = discountPercent;
        this.type = type;
        this.createdAt = null;
    }

    public VoucherDto(Voucher voucher) {
        this(voucher.getVoucherId(), voucher.getValue(), voucher.getType().toString(), voucher.getCreatedAt());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getDiscountPercent() {
        return discountPercent;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
