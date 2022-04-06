package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends AbstractVoucher {

    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        super(voucherId);
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId+" + getVoucherId() +
                "percent=" + percent +
                '}';
    }
}
