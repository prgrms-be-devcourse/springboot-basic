package com.programmers.vouchermanagement.voucher;

import java.util.UUID;

public class PercentVoucher implements Voucher {
    private final UUID voucherID;
    private final long discountPercent;

    public PercentVoucher(UUID voucherID, long discountPercent) {
        this.voucherID = voucherID;
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public long discount(long priceBeforeDiscount) {
        return (long) (priceBeforeDiscount * (1 - discountPercent / 100D));
    }

    @Override
    public boolean validatePositiveDiscount() {
        return discountPercent > 0;
    }
}
