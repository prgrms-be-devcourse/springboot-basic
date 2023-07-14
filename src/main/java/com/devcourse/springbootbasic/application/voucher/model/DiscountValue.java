package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Objects;

public class DiscountValue {

    private final VoucherType voucherType;
    private final double value;

    public DiscountValue(VoucherType voucherType, String valueString) {
        double parsedValue = parseDiscountValue(valueString);
        validatePositive(parsedValue);
        validatePercent(voucherType, parsedValue);
        this.voucherType = voucherType;
        this.value = parsedValue;
    }

    public DiscountValue(VoucherType voucherType, double valueDouble) {
        validatePositive(valueDouble);
        validatePercent(voucherType, valueDouble);
        this.voucherType = voucherType;
        this.value = valueDouble;
    }

    private double parseDiscountValue(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            throw new InvalidDataException(ErrorMessage.INVALID_VALUE.getMessageText(), e.getCause());
        }
    }

    private void validatePositive(double parsedValue) {
        if (parsedValue < 0) {
            throw new InvalidDataException(ErrorMessage.INVALID_VALUE.getMessageText());
        }
    }

    private void validatePercent(VoucherType voucherType, double parsedValue) {
        if (Objects.equals(voucherType, VoucherType.PERCENT_DISCOUNT) && parsedValue > 100) {
            throw new InvalidDataException(ErrorMessage.INVALID_VALUE.getMessageText());
        }
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountValue that = (DiscountValue) o;
        return Double.compare(that.value, value) == 0 && voucherType == that.voucherType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherType, value);
    }
}
