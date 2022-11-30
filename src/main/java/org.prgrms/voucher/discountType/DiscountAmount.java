package org.prgrms.voucher.discountType;


import java.util.Objects;

public class DiscountAmount implements Amount {

    private final long value;

    public DiscountAmount(long input) {
        validateAmount(input);
        this.value = input;
    }

    private void validateAmount(long input) {
        if (input < 1) {
            throw new IllegalStateException(
                "Discount amount cannot be negative *currentAmount: " + input);
        }
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscountAmount that = (DiscountAmount) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
