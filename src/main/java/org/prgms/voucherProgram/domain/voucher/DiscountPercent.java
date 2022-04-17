package org.prgms.voucherProgram.domain.voucher;

import java.io.Serializable;
import java.util.Objects;

import org.prgms.voucherProgram.exception.WrongDiscountPercentException;

public class DiscountPercent implements Serializable {
    private static final long MIN_PERCENT = 1;
    private static final long MAX_PERCENT = 100;

    private final Long percent;

    public DiscountPercent(Long percent) {
        validateDiscountPercent(percent);
        this.percent = percent;
    }

    private void validateDiscountPercent(long discountPercent) {
        if (isWrongPercent(discountPercent)) {
            throw new WrongDiscountPercentException();
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
        DiscountPercent that = (DiscountPercent)o;
        return percent.equals(that.percent);
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
