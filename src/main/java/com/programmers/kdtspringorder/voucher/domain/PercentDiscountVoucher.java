package com.programmers.kdtspringorder.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent <= 0 || percent > 50) {
            throw new IllegalArgumentException("percent should over than 0 And less or equal to 50");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * percent / 100;
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public String toString() {
        return "voucherId=" + voucherId +
                ", discountPercent=" + percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PercentDiscountVoucher that = (PercentDiscountVoucher) o;

        if (percent != that.percent) return false;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        int result = voucherId.hashCode();
        result = 31 * result + (int) (percent ^ (percent >>> 32));
        return result;
    }
}
