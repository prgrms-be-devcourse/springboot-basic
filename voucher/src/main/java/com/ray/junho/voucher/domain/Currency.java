package com.ray.junho.voucher.domain;

import java.util.Objects;

public class Currency {

    private final double value;

    public static Currency of(double value) {
        return new Currency(value);
    }

    private Currency(double value) {
        validateInput(value);
        this.value = value;
    }

    private void validateInput(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("0보다 작은 화폐는 발행할 수 없습니다.");
        }
    }

    public Currency minus(Currency value) {
        return Currency.of(this.value - value.value);
    }

    public boolean isLessThan(Currency value) {
        return this.value < value.value;
    }

    public Currency multiply(Currency value) {
        return Currency.of(this.value * value.value);
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
