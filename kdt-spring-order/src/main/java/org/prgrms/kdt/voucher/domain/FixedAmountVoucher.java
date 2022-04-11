package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final int discountAmount;

    public FixedAmountVoucher(UUID voucherId, int discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() { return voucherId; }

    @Override
    public int getDiscountRate() { return discountAmount; }

    @Override
    public VoucherType getVoucherType() { return VoucherType.FIXED; }

    @Override
    public String toString() {
        return "FixedAmountVoucher : -" + discountAmount + "$ discount";
    }
}
