package org.devcourse.springbasic.voucher;

import org.devcourse.springbasic.menu.RunByCreatesMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountRate() {
        return percent;
    }


    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher" +
                "," + voucherId +
                "," + percent;
    }
}
