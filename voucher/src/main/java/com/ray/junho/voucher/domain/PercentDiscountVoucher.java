package com.ray.junho.voucher.domain;

import java.util.Objects;

public class PercentDiscountVoucher implements Voucher {

    private final long id;

    private final int discountRate;

    public PercentDiscountVoucher(long id, int discountRate) {
        this.id = id;
        this.discountRate = discountRate;
        validatePercentage(discountRate);
    }

    private void validatePercentage(int discountRate) {
        if (discountRate <= 0 || discountRate > 100) {
            throw new IllegalArgumentException("퍼센트는 0초과 100이하여야 합니다.");
        }
    }

    @Override
    public Currency discount(Currency beforeDiscount) {
        return beforeDiscount.discountedWithPercentage(discountRate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return id == that.id && discountRate == that.discountRate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discountRate);
    }
}
