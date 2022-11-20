package org.prgrms.java.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected final long amount;
    protected final String type;
    protected boolean used;
    protected LocalDateTime expiredAt;

    public Voucher(UUID voucherId, long amount, String type, boolean used, LocalDateTime expiredAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.type = type;
        this.used = used;
        this.expiredAt = expiredAt;
    }

    public UUID getVoucherId() {
        return this.voucherId;
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

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public abstract long discount(long beforeDiscount);

    @Override
    public abstract String toString();
}
