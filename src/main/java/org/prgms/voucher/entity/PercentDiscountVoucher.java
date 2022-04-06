package org.prgms.voucher.entity;

import java.util.UUID;

import org.prgms.voucher.exception.ZeroDiscountPercentException;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountPercent;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) throws ZeroDiscountPercentException {
        if (discountPercent == 0) {
            throw new ZeroDiscountPercentException();
        }
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * (1 - (discountPercent / 100.0)));
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
