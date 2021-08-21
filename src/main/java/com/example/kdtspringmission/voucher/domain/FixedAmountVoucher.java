package com.example.kdtspringmission.voucher.domain;

import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID id;
    private final long amount;

    public FixedAmountVoucher(UUID id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID id) {
        this(id, 100L);
    }

    @Override
    public long discountPrice(long price) {
        return price - amount;
    }

    public UUID getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "id=" + id +
            ", amount=" + amount +
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
