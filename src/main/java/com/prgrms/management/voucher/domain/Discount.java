package com.prgrms.management.voucher.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Discount {

    private UUID voucherId;
    private LocalDateTime createdAt;
    private UUID customerId;

    protected Discount() {
        this.voucherId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    protected Discount(UUID voucherId, LocalDateTime createdAt, UUID customerId) {
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

}
