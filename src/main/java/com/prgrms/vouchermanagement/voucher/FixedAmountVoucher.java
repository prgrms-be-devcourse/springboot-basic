package com.prgrms.vouchermanagement.voucher;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {

    private final UUID voucherId;
    private final long discountPrice;

    public FixedAmountVoucher(UUID voucherId, long discountPrice) {
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice - discountPrice;
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "Id=" + getId() + ", Type=FixedAmount, discountPrice="+ discountPrice +"won";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return discountPrice == that.discountPrice && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountPrice);
    }
}
