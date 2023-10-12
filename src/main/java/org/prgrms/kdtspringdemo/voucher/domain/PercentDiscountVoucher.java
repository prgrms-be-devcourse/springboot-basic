package org.prgrms.kdtspringdemo.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private UUID voucherId;
    private long percent;
    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }
}
