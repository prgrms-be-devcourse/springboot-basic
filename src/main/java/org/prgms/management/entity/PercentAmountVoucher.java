package org.prgms.management.entity;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final int percent;
    private final String voucherName;
    private final String voucherType;

    public PercentAmountVoucher(UUID voucherId, int percent, String voucherName, String voucherType) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherName = voucherName;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
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
