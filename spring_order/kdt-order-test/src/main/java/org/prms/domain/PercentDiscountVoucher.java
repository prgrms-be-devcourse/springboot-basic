package org.prms.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucher;
    private final long percent;

    public PercentDiscountVoucher(UUID voucher, long percent) {
        this.voucher = voucher;
        this.percent = percent;
    }


    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent/100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucher=" + voucher +
                ", percent=" + percent +
                '}';
    }
}
