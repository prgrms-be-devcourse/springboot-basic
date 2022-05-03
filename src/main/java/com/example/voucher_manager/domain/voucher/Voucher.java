package com.example.voucher_manager.domain.voucher;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher implements Serializable {

    protected final UUID voucherId;
    protected UUID ownerId;
    protected LocalDateTime createdAt;

    protected Voucher(UUID voucherId, UUID ownerId, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void provideToCustomer(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract Long discount(Long regularPrice);
    public abstract VoucherType getVoucherType();
    public abstract Long getDiscountInformation();
}
