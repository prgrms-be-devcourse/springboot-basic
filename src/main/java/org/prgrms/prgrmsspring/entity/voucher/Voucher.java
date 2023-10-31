package org.prgrms.prgrmsspring.entity.voucher;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected final long amount;
    protected final String type;
    protected final LocalDateTime createTime;

    protected Voucher(UUID voucherId, long amount, String type, LocalDateTime createTime) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.type = type;
        this.createTime = createTime;
    }

    protected Voucher(UUID voucherId, long amount, String type) {
        this(voucherId, amount, type, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Voucher voucher = (Voucher) object;
        return amount == voucher.amount && Objects.equals(voucherId, voucher.voucherId) && Objects.equals(type, voucher.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, amount, type);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    protected abstract long discount(long beforeDiscount);
}
