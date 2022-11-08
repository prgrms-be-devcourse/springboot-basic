package org.prgrms.kdt.voucher;

import java.util.Objects;

public class VoucherAmount {

    private Long value;

    public VoucherAmount(String amount) {
        try {
            this.value = Long.parseLong(amount);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Please enter an integer." + System.lineSeparator());
        }
    }

    public boolean isGreaterThanEqual(Long value) {
        return this.value >= value;
    }

    public boolean isLessThanEqual(Long value) {
        return this.value <= value;
    }

    public boolean isGreaterThan(long value) {
        return this.value > value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoucherAmount)) return false;
        VoucherAmount that = (VoucherAmount) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
