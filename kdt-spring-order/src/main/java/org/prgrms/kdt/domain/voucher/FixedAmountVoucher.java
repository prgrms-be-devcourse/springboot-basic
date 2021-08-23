package org.prgrms.kdt.domain.voucher;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private static final long MAX_VOUCHER_VALUE = 1000000;
    private final UUID voucherId;
    private final long value;

    public FixedAmountVoucher(UUID voucherId, long value) {
        if (value < 0) throw new IllegalArgumentException("Value should be positive");
        if (value == 0) throw new IllegalArgumentException("Value should not be zero");
        if (value == MAX_VOUCHER_VALUE)
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
        return discounted < 0 ? 0 : discounted;
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
        return MessageFormat.format("{0} {1} {2}", voucherId, VoucherType.FIX, value);
    }
}
