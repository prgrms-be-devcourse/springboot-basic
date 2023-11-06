package org.prgrms.kdt.voucher.Dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucherDto {
    private final UUID voucherId;
    private final int percent;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucherDto(UUID voucherId, int percent, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public int getPercent() {
        return percent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
