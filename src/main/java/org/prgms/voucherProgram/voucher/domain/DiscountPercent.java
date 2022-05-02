package org.prgms.voucherProgram.voucher.domain;

import java.io.Serializable;
import java.util.Objects;

import org.prgms.voucherProgram.voucher.exception.WrongDiscountValueException;

public class DiscountPercent implements Serializable {
    private static final long MIN_PERCENT = 1;
    private static final long MAX_PERCENT = 100;

    private final long percent;

    public DiscountPercent(long percent) {
        validateDiscountPercent(percent);
        this.percent = percent;
    }

    private void validateDiscountPercent(long discountPercent) {
        if (isWrongPercent(discountPercent)) {
            throw new WrongDiscountValueException("[ERROR] 올바른 할인퍼센트가 아닙니다.");
        }
    }

    private boolean isWrongPercent(long discountPercent) {
        return MAX_PERCENT < discountPercent || discountPercent < MIN_PERCENT;
    }

    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * (1 - (percent / 100.0)));
    }

    public Long getPercent() {
        return percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DiscountPercent other = (DiscountPercent)o;
        return this.percent == other.percent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percent);
    }

    @Override
    public String toString() {
        return String.valueOf(percent);
    }
}
