package com.prgrms.management.voucher.domain;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType;

    public PercentAmountVoucher(long percent) {
        this.voucherId = UUID.randomUUID();
        this.percent = percent;
        this.voucherType=VoucherType.PERCENT;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}