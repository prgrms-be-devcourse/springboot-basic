package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;
    private UUID customerId;
    private final long value;
    private final VoucherType type;
    private boolean used;
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;

    public Voucher(UUID voucherId, UUID customerId, long value, VoucherType type, boolean used, LocalDateTime createdAt, LocalDateTime expirationDate) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.value = value;
        this.type = type;
        this.used = used;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long discount(long beforeDiscount);

    public void allocateVoucherToCustomer(UUID customerId) {
        this.customerId = customerId;
    }

    public void removeVoucherFromCustomer() {
        this.customerId = null;
    }

    public void use() {
        used = true;
    }

    public long getValue() {
        return value;
    }

    public VoucherType getType() {
        return type;
    }

    public boolean isUsed() {
        return used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return voucherId.equals(voucher.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}
