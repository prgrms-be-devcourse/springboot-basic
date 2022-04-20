package org.prgms.voucherProgram.domain.voucher;

import java.io.Serializable;
import java.util.Objects;

import org.prgms.voucherProgram.exception.WrongDiscountValueException;

public class DiscountAmount implements Serializable {
    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 1_000_000;

    private final long amount;

    public DiscountAmount(long amount) {
        validateDiscountAmount(amount);
        this.amount = amount;
    }

    private void validateDiscountAmount(long discountAmount) {
        if (isWrongAmount(discountAmount)) {
            throw new WrongDiscountValueException("[ERROR] 올바른 할인금액이 아닙니다.");
        }
    }

    private boolean isWrongAmount(long discountAmount) {
        return MAX_AMOUNT < discountAmount || discountAmount < MIN_AMOUNT;
    }

    public boolean isBigger(long beforeDiscount) {
        return beforeDiscount <= this.amount;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DiscountAmount other = (DiscountAmount)o;
        return this.amount == other.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
