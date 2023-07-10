package com.wonu606.vouchermanager.domain.voucher.price;

public class Price {

    private final double value;

    public Price(double value) {
        validateValue(value);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    private void validateValue(double value) {
        if (isNotPositive(value)) {
            throw new IllegalArgumentException("가격은 양수여야 합니다.");
        }
    }

    private boolean isNotPositive(double value) {
        return value < 0;
    }
}
