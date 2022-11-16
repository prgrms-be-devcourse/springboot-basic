package org.prgrms.springorder.domain.voucher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;

    private final long amount;

    private UUID customerId;

    private final LocalDateTime createdAt;

    public UUID getVoucherId() {
        return voucherId;
    }

    protected Voucher(UUID voucherId, long amount) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    protected Voucher(UUID voucherId, long amount, UUID customerId, LocalDateTime createdAt) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public void changeCustomerId(UUID customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("customer Id is null");
        }

        this.customerId = customerId;
    }

    public void removeCustomerId() {
        this.customerId = null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    protected abstract void validateAmount(long amount);

    public long getAmount() {
        return amount;
    }

    public abstract VoucherType getVoucherType();

    public abstract long discount(long beforeDiscount);

    @Override
    public String toString() {
        return String.format("voucherType = %s, id = %s, amount = %s, customerId = %s, createdAt = %s", getVoucherType(), getVoucherId(), getAmount(), getCustomerId(), getCreatedAt());
    }

    public boolean isOwned(UUID customerId) {
        return Objects.equals(this.customerId, customerId);
    }

}
