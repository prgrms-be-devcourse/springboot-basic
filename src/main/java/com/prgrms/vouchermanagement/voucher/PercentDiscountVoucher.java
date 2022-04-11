package com.prgrms.vouchermanagement.voucher;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {

    private UUID voucherId;
    private long discountPercentage;

    public PercentDiscountVoucher(UUID voucherId, long discountPercentage) {
        this.voucherId = voucherId;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice * (discountPercentage / 100);
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "Id=" + getId() + ", Type=PercentDiscount, discountPercent=" + discountPercentage + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return discountPercentage == that.discountPercentage && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountPercentage);
    }
}
