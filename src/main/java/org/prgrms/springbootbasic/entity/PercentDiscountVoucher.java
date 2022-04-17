package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        super(voucherId);
        this.percent = percent;
    }

    public int getPercent() {
        return percent;
    }
}
