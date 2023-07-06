package com.prgrms.model.voucher.discount;

public abstract class Discount {
    private final String negativeException = "할인에 대한 인자값은 양수여야 합니다.";
    private final double value;

    public Discount(double value) {
        validPositiveDiscount(value < 0);
        validLimit(value);
        this.value = value;
    }

    private void validPositiveDiscount(boolean expression) {
        if (expression) {
            throw new IllegalArgumentException(negativeException);
        }
    }

    protected abstract void validLimit(double value) ;

    public double getValue() {
        return value;
    }
}
