package org.programmers.kdtspring.entity.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final int amount;
    private final String voucherType;

    public FixedAmountVoucher(UUID voucherId, int amount, String voucherType) {
        super(voucherId);
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, int amount, String voucherType) {
        super(voucherId, customerId);
        this.amount = amount;
        this.voucherType = voucherType;
    }

    @Override
    public int getDiscount() {
        return this.amount;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }
}
