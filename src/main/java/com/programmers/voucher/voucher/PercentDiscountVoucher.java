package com.programmers.voucher.voucher;


import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percentage;

    PercentDiscountVoucher(UUID voucherId, long percentage) {
        this.voucherId = voucherId;
        this.percentage = percentage;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percentage / 100);
    }

    @Override
    public String toString() {
        return "type=PercentDiscountVoucher" + '\n' +
                "percentage=" + percentage;
    }

    @Override
    public long getValue() {
        return percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return percentage == that.percentage && Objects.equals(getVoucherId(), that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId(), percentage);
    }
}
