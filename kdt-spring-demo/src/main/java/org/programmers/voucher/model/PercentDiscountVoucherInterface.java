package org.programmers.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucherInterface implements VoucherInterface {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucherInterface(UUID voucherId, long percent) {
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
    public long getVoucherValue() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }
}
