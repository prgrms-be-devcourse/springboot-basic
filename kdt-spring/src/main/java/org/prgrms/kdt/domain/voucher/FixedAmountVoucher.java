package org.prgrms.kdt.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private long value;
    private final VoucherType voucherType = VoucherType.FIX;

    public FixedAmountVoucher(UUID voucherId, long value, LocalDateTime createdAt) throws IllegalArgumentException {
        if (isUnderZero(value)) throw new IllegalArgumentException("Value should be positive");
        if (isZero(value)) throw new IllegalArgumentException("Value should not be zero");
        if (isOverMaxValue(value))
            throw new IllegalArgumentException("Value should be under " + getMaxValue());
        this.value = value;
        this.voucherId = voucherId;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS);
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public VoucherType getType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discounted = beforeDiscount - value;
        return isUnderZero(discounted) ? 0 : discounted;
    }

    @Override
    public Voucher changeValue(long value) {
        if (isUnderZero(value)) throw new IllegalArgumentException("Value should be positive");
        if (isZero(value)) throw new IllegalArgumentException("Value should not be zero");
        if (isOverMaxValue(value))
            throw new IllegalArgumentException("Value should be under " + getMaxValue());

        this.value = value;

        return this;
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
        return String.format("%s %s %s", voucherId, voucherType, value);
    }

    @Override
    public long getMaxValue() {
        return voucherType.getMaxValue();
    }

    private boolean isOverMaxValue(long value) {
        return value > getMaxValue();
    }

    private boolean isZero(long value) {
        return value == 0;
    }

    private boolean isUnderZero(long value) {
        return value < 0;
    }
}
