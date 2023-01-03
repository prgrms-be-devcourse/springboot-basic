package org.prgrms.kdt.model.voucher;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;
    private final double discountAmount;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private UUID ownedCustomerId;

    public Voucher(UUID voucherId, String discountAmount, VoucherType voucherType, UUID ownedCustomerId, LocalDateTime createdAt) {
        validate(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = Double.parseDouble(discountAmount);
        this.voucherType = voucherType;
        this.ownedCustomerId = ownedCustomerId;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Optional<UUID> getOwnedCustomerId() {
        return Optional.ofNullable(ownedCustomerId);
    }

    public void setOwnedCustomerId(UUID ownedCustomerId) {
        this.ownedCustomerId = ownedCustomerId;
    }

    public void removeOwnedCustomer() {
        this.ownedCustomerId = null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                ", voucherType='" + voucherType + '\'' +
                '}';
    }

    protected abstract void validate(String discountAmount);
}
