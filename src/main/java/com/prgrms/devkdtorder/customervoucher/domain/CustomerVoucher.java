package com.prgrms.devkdtorder.customervoucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerVoucher {

    private final UUID customerVoucherId;
    private final UUID customerId;
    private final UUID voucherId;
    private boolean used;
    private LocalDateTime usedAt;
    private LocalDateTime createdAt;
    private final LocalDateTime expiredAt;

    public CustomerVoucher(UUID customerVoucherId, UUID customerId, UUID voucherId, LocalDateTime expiredAt) {
        this.customerVoucherId = customerVoucherId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.expiredAt = expiredAt;
    }

    public CustomerVoucher(UUID customerVoucherId, UUID customerId, UUID voucherId, boolean used, LocalDateTime usedAt, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this(customerVoucherId, customerId, voucherId, expiredAt);
        this.used = used;
        this.usedAt = usedAt;
        this.createdAt = createdAt;
    }

    public UUID getCustomerVoucherId() {
        return customerVoucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public boolean isUsed() {
        return used;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
