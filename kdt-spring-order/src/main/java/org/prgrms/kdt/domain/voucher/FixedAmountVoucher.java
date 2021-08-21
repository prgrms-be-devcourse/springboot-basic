package org.prgrms.kdt.domain.voucher;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID voucherId;
    private final long value;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.value = amount;
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
        return beforeDiscount - value;
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
