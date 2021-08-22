package org.prgrms.devcourse.domain;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private final UUID voucherId;
    private final long percent;

    private PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public static PercentDiscountVoucher of(UUID voucherId, long percent) {
        return new PercentDiscountVoucher(voucherId, percent);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * percent / 100;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher," + voucherId + "," + percent + "\n";
    }

}
