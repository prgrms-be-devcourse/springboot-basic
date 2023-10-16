package com.programmers.vouchermanagement;

import java.util.UUID;

public class PercentVoucher implements Voucher {
    private final UUID voucherID;
    private final double discountPercent;

    public PercentVoucher(UUID voucherID, double discountPercent) {
        this.voucherID = voucherID;
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public long discount(long priceBeforeDiscount) {
        return (long) (priceBeforeDiscount * (1 - discountPercent / 100));
    }
}
