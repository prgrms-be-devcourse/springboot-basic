package org.prgrms.kdt.voucher.Dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucherDto {
    private final UUID voucherId;
    private final long amount;
    private final LocalDateTime createdAt;

    public FixedAmountVoucherDto(UUID voucherId, long amount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
