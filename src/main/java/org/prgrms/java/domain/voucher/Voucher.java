package org.prgrms.java.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected UUID ownerId;
    protected final long amount;
    protected final String type;
    protected boolean used;
    protected final LocalDateTime createdAt;
    protected LocalDateTime expiredAt;

    public Voucher(UUID voucherId, UUID ownerId, long amount, String type, boolean used, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.voucherId = voucherId;
        this.ownerId = ownerId;
        this.amount = amount;
        this.type = type;
        this.used = used;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public UUID getVoucherId() {
        return this.voucherId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public long getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public boolean isUsed() {
        return used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public abstract long discount(long beforeDiscount);

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %s, %s, %s, %s", voucherId, ownerId, amount, type, createdAt, expiredAt, used);
    }
}
