package org.prgrms.kdt.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final int amount;
    private final LocalDateTime createdAt;
    private final String type;
    private UUID customerId;

    public VoucherDto(UUID voucherId, int amount, String type, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public VoucherDto(UUID voucherId, int amount, LocalDateTime createdAt, String type, UUID customerId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.type = type;
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getType() {
        return type;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
