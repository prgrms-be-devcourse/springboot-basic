package com.prgrms.springbootbasic.domain;

import java.util.UUID;

public class FixedDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long fixedDiscount;

    public FixedDiscountVoucher(UUID voucherId, long fixedDiscount) {
        this.voucherId = voucherId;
        this.fixedDiscount = fixedDiscount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return fixedDiscount;
    }
}
