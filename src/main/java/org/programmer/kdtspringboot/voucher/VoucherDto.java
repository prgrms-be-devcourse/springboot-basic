package org.programmer.kdtspringboot.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final Long discountAmount;
    private final String type;
    private final LocalDateTime createdAt;

    public VoucherDto(UUID voucherId, Long discountAmount, String type, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public VoucherDto() {
        this.voucherId = null;
        this.discountAmount = null;
        this.type = null;
        this.createdAt = null;
    }

    public VoucherDto(Long discountAmount, String type) {
        this.voucherId = null;
        this.discountAmount = discountAmount;
        this.type = type;
        this.createdAt = null;
    }

    public VoucherDto(Voucher voucher) {
        this(voucher.getVoucherId(), voucher.getValue(), voucher.getType().toString(), voucher.getCreatedAt());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
