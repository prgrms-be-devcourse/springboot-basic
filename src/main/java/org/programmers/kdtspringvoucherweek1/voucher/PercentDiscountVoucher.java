package org.programmers.kdtspringvoucherweek1.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getDiscount() {
        return percent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher\t" +
                voucherId + "\t" +
                percent + "%" + "\t";
    }
}
