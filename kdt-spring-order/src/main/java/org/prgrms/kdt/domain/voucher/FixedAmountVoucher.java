package org.prgrms.kdt.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final long MAX_VOUCHER_VALUE = 1000000;
    private final UUID voucherId;
    private final long value;

    public FixedAmountVoucher(UUID voucherId, long value) throws IllegalArgumentException {
        if (isUnderZero(value)) throw new IllegalArgumentException("Value should be positive");
        if (isZero(value)) throw new IllegalArgumentException("Value should not be zero");
        if (isOverMaxValue(value))
            throw new IllegalArgumentException("Value should be under " + MAX_VOUCHER_VALUE);
        this.value = value;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discounted = beforeDiscount - value;
        return isUnderZero(discounted) ? 0 : discounted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher voucher = (FixedAmountVoucher) o;
        return value == voucher.value && Objects.equals(voucherId, voucher.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, value);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", voucherId, VoucherType.FIX, value);
    }

    private boolean isOverMaxValue(long value) {
        return value >= MAX_VOUCHER_VALUE;
    }

    private boolean isZero(long value) {
        return value == 0;
    }

    private boolean isUnderZero(long value) {
        return value < 0;
    }
}
