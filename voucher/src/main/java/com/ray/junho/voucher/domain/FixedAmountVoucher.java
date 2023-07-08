package com.ray.junho.voucher.domain;

import java.util.Objects;

public class FixedAmountVoucher implements Voucher {

    private final long id;

    private final int discountValue;

    public FixedAmountVoucher(long id, int discountValue) {
        validateDiscountValue(discountValue);
        this.id = id;
        this.discountValue = discountValue;
    }

    private void validateDiscountValue(int discountValue) {
        if (discountValue <= 0) {
            throw new IllegalArgumentException("할인 금액이 0보다 큰 바우처만 생성할 수 있습니다");
        }
    }

    @Override
    public Currency discount(Currency beforeDiscount) {
        validateFixedAmount(beforeDiscount);
        return beforeDiscount.minus(Currency.of(discountValue));
    }

    private void validateFixedAmount(Currency beforeDiscount) {
        if (beforeDiscount.isLessThan(Currency.of(discountValue))) {
            throw new IllegalArgumentException("기존 금액 보다 바우처의 할인율이 더 큽니다");
        }
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

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "id=" + id +
                ", discountValue=" + discountValue +
                '}';
    }
}
