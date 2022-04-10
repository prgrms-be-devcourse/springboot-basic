package org.prgms.management.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final String voucherName;

    public FixedAmountVoucher(UUID voucherId, long amount, String voucherName) {
        this.voucherId = voucherId;
        this.amount = amount;
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
        return amount;
    }
}
