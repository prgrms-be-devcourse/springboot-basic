package com.wonu606.vouchermanager.domain.voucher.discountvalue;

public abstract class DiscountValue {

    protected double value;

    public DiscountValue(double value) {
        validateValue(value);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DiscountValue{" + "value=" + value + '}';
    }

    protected abstract void validateValue(double value);
}
