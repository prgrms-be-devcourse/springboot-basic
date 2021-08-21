package org.prgrms.kdt.domain.voucher;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long value;

    public PercentAmountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.value = percent;
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
        return beforeDiscount * (value / 100);
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
        return MessageFormat.format("{0} {1} {2}", voucherId, VoucherType.PERCENT, value);
    }
}
