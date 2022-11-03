package org.programmers.kdtspringdemo.voucher.model;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
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
    public String getVoucherInfo() {
        return MessageFormat.format("PercentVoucher, percent: {0}", percent);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}
