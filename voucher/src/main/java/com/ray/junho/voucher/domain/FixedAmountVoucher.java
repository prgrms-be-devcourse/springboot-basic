package com.ray.junho.voucher.domain;

import java.util.Objects;

public class FixedAmountVoucher implements Voucher {

    private final long id;

    private final int discountValue;

    public FixedAmountVoucher(long id, int discountValue) {
        this.id = id;
        validateDiscountValue(discountValue);
        this.discountValue = discountValue;
    }

    private void validateDiscountValue(int discountValue) {
        if (discountValue <= 0) {
            throw new IllegalArgumentException("할인 금액이 0보다 큰 바우처만 생성할 수 있습니다");
        }
    }

    @Override
    public Currency discount(Currency beforeDiscount) {
        return beforeDiscount.discountedWithFixedPrice(Currency.of(discountValue));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return id == that.id && discountValue == that.discountValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discountValue);
    }
}
