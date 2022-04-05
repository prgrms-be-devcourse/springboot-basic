package org.prgms.springbootbasic.management.entity;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final String voucherName;

    public PercentAmountVoucher(UUID voucherId, long percent, String voucherName) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherName = voucherName;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getVoucherName() {
        return voucherName;
    }

    @Override
    public long getDiscountNum() {
        return percent;
    }
}
