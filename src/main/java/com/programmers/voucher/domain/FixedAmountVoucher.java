package com.programmers.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long originPrice) {
        return originPrice - discountAmount;
    }

}
