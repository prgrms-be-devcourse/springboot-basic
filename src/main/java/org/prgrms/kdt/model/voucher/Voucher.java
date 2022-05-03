package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final long discountAmount;
    private final LocalDateTime createAt;
    private Customer customer;
    private LocalDateTime ownedAt;
    private final int voucherType;

    public Voucher(UUID voucherId, long discountAmount, LocalDateTime createAt, int voucherType) {
        validateDiscountAmount(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.createAt = createAt;
        this.voucherType = voucherType;
    }

    public Voucher(UUID voucherId, long discountAmount, LocalDateTime createAt, Customer customer, LocalDateTime ownedAt, int voucherType) {
        validateDiscountAmount(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.createAt = createAt;
        this.customer = customer;
        this.ownedAt = ownedAt;
        this.voucherType = voucherType;
    }

    abstract void validateDiscountAmount(long discountAmount);

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getOwnedAt() {
        return ownedAt;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                ", createAt=" + createAt +
                ", customer=" + customer +
                ", ownedAt=" + ownedAt +
                ", voucherType=" + voucherType +
                '}';
    }
}
