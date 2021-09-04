package com.example.kdtspringmission.voucher.domain;

import com.example.kdtspringmission.customer.domain.Customer;
import java.util.Objects;
import java.util.UUID;

public class RateAmountVoucher implements Voucher {

    private final UUID id;
    private final long discountAmount;
    private UUID ownerId;

    public RateAmountVoucher(UUID id, long discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }

    public RateAmountVoucher(UUID id, long discountAmount, UUID ownerId) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.ownerId = ownerId;
    }

    public RateAmountVoucher(UUID id) {
        this(id, 10L);
    }

    @Override
    public long discount(long price) {
        return (long) (price - (price * ((double) discountAmount / 100)));
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String getTypeName() {
        return this.getClass().getSimpleName();
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public long getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public String toString() {
        return "RateAmountVoucher{" +
            "id=" + id +
            ", discountAmount=" + discountAmount +
            ", ownerId=" + ownerId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateAmountVoucher that = (RateAmountVoucher) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
