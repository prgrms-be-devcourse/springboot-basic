package org.prms.kdtordertest;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private final UUID voucher;
    private final long percnet;

    public PercentDiscountVoucher(UUID voucher, long percnet) {
        this.voucher = voucher;
        this.percnet = percnet;
    }


    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percnet/100);
    }
}
