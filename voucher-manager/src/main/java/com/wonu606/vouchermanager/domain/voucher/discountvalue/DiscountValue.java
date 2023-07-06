package com.wonu606.vouchermanager.domain.voucher.discountvalue;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class DiscountValue {

    protected double value;

    public DiscountValue(double value) {
        validateValue(value);
        this.value = value;
    }

    protected abstract void validateValue(double value);
}
