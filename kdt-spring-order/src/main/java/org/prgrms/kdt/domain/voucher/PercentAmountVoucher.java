package org.prgrms.kdt.domain.voucher;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {

    private static final long MAX_VOUCHER_VALUE = 100;
    private final UUID voucherId;
    private final long value;

    public PercentAmountVoucher(UUID voucherId, long percent) throws IllegalArgumentException{
        if (isUnderZero(percent)) throw new IllegalArgumentException("Percent should be positive");
        if (isZero(percent)) throw new IllegalArgumentException("Percent should not be zero");
        if (isOverMaxValue(percent)) throw  new IllegalArgumentException("Percent should be less than " + MAX_VOUCHER_VALUE);
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
        return String.format("%s %s %s", voucherId, VoucherType.PERCENT, value);
    }

    private boolean isOverMaxValue(long percent) {
        return percent > MAX_VOUCHER_VALUE;
    }

    private boolean isZero(long percent) {
        return percent == 0;
    }

    private boolean isUnderZero(long percent) {
        return percent < 0;
    }

}
