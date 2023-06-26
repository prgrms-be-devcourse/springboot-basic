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

    public long minus(long value) {
        return this.value - value;
    }

    public boolean isLessThan(int value) {
        return this.value < value;
    }

    public long multiply(double value) {
        return (long) (this.value * value);
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
