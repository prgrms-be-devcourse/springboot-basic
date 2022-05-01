package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final int discountAmount;

    public PercentDiscountVoucher(UUID voucherId, int discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() { return voucherId; }

    @Override
    public VoucherType getVoucherType() { return VoucherType.PERCENT; }

    @Override
    public int getDiscountRate() { return discountAmount; }

    @Override
    public String toString() {
        return "PercentDiscountVoucher : " + discountAmount + "% discount";
    }
}
