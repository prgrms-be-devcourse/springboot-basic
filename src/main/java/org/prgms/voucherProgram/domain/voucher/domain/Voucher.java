package org.prgms.voucherProgram.domain.voucher.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Voucher implements Serializable {

    protected final UUID voucherId;
    protected final LocalDateTime createdTime;
    protected UUID customerId;

    protected Voucher(UUID voucherId, LocalDateTime createdTime) {
        this(voucherId, null, createdTime);
    }

    protected Voucher(UUID voucherId, UUID customerId, LocalDateTime createdTime) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.createdTime = createdTime;
    }

    public void assignCustomer(UUID customerId) {
        this.customerId = customerId;
    }

    public boolean isSameVoucher(UUID voucherId) {
        return this.voucherId.equals(voucherId);
    }

    public boolean isAssign() {
        return Objects.nonNull(customerId);
    }

    public boolean isNotAssign() {
        return Objects.isNull(customerId);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public abstract long discount(long beforeDiscount);

    public abstract int getType();

    public abstract long getDiscountValue();
}
