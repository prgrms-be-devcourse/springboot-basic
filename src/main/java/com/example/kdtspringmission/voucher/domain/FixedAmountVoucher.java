package com.example.kdtspringmission.voucher.domain;

import com.example.kdtspringmission.customer.domain.Customer;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID id;
    private final long discountAmount;
    private Customer owner;

    public FixedAmountVoucher(UUID id, long discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }

    public FixedAmountVoucher(UUID id) {
        this(id, 100L);
    }

    @Override
    public long discount(long price) {
        return price - discountAmount;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean support(VoucherType type) {
        return type.equals(VoucherType.FIX);
    }

    @Override
    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Customer getOwner() {
        return owner;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "id=" + id +
            ", amount=" + discountAmount +
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
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
