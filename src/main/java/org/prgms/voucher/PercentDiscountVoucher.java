package org.prgms.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final long discountPercent;
    private final UUID voucherId;

    public PercentDiscountVoucher(long discountPercent, UUID voucherId) {
        this.discountPercent = discountPercent;
        this.voucherId = voucherId;
    }

    @Override
    public long apply(long beforeDiscount) {
        return (long) ((1 - (discountPercent / 100.0)) * beforeDiscount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
