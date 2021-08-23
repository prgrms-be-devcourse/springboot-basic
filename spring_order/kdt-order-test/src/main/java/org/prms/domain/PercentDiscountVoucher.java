package org.prms.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final String type="PERCENT";


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
        return beforeDiscount * (percent/100);
    }

    @Override
    public long getAmount() {
        return percent;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucher=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
