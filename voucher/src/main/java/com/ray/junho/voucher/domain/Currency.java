package com.ray.junho.voucher.domain;

import java.util.Objects;

public class Currency {
    private final long value;

    public static Currency of(long value) {
        return new Currency(value);
    }

    private Currency(long value) {
        validateInput(value);
        this.value = value;
    }

    private void validateInput(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("0보다 작은 화폐는 발행할 수 없습니다.");
        }
    }

    public Currency discountedWithFixedPrice(Currency discountValue) {
        validateFixedAmount(discountValue);
        return new Currency(this.value - discountValue.value);
    }

    private void validateFixedAmount(Currency discountValue) {
        if (this.value < discountValue.value) {
            throw new IllegalArgumentException("기존 금액 보다 바우처의 할인율이 더 큽니다");
        }
    }

    public Currency discountedWithPercentage(int discountRate) {
        long discountedValue = (long) (this.value * ((double) discountRate / 100));
        return new Currency(this.value - discountedValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return value == currency.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
