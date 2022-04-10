package org.prgms.management.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final String voucherName;
    private final String voucherType;

    public FixedAmountVoucher(UUID voucherId, long amount, String voucherName, String voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
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
        return amount;
    }
}
