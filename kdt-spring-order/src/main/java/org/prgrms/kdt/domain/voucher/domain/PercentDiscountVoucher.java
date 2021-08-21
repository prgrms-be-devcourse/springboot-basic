package org.prgrms.kdt.domain.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100); // 여기 틀린 것 같음.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PercentDiscountVoucher that = (PercentDiscountVoucher) o;

        return percent == that.percent;
    }

    @Override
    public int hashCode() {
        return (int) (percent ^ (percent >>> 32));
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}