package com.prgrms.management.voucher.domain;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType;

    public PercentAmountVoucher(UUID voucherId, long percent,VoucherType voucherType) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType=voucherType;
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