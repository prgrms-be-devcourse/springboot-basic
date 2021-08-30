package org.prgrms.kdt.domain.voucher;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {

    private final UUID voucherId;
    private long value;
    private final LocalDateTime createdAt;
    private final VoucherType voucherType = VoucherType.PERCENT;

    public PercentAmountVoucher(UUID voucherId, long value, LocalDateTime createdAt) throws IllegalArgumentException{
        if (isUnderZero(value)) throw new IllegalArgumentException("Percent should be positive");
        if (isZero(value)) throw new IllegalArgumentException("Percent should not be zero");
        if (isOverMaxValue(value)) throw  new IllegalArgumentException("Percent should be less than " + getMaxValue());
        this.voucherId = voucherId;
        this.value = value;
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
    public long getMaxValue() {
        return voucherType.getMaxValue();
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (value / 100);
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
        PercentAmountVoucher that = (PercentAmountVoucher) o;
        return value == that.value && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, value);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", voucherId, VoucherType.PERCENT, value);
    }

    private boolean isOverMaxValue(long percent) {
        return percent > getMaxValue();
    }

    private boolean isZero(long percent) {
        return percent == 0;
    }

    private boolean isUnderZero(long percent) {
        return percent < 0;
    }

}
