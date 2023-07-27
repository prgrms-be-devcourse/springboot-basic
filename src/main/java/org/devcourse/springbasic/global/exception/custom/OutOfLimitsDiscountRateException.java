package org.devcourse.springbasic.global.exception.custom;

public class OutOfLimitsDiscountRateException extends IllegalArgumentException {
    public OutOfLimitsDiscountRateException(String msg) {
        super(msg);
    }
}
