package com.prgrms.management.voucher.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Voucher {

    private UUID voucherId;
    private LocalDateTime createdAt;
    private UUID customerId;

    public Voucher(UUID customerId) {
        this.voucherId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.customerId = customerId;
    }

    public Voucher(UUID voucherId, LocalDateTime createdAt, UUID customerId) {
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }

    public String serialized() {
        return this.getVoucherId() + "," + this.getAmount() + "," + this.getVoucherType();
    }

    public abstract VoucherType getVoucherType();

    public abstract long getAmount();
}
