package org.prgms.voucher.entity;

import java.util.UUID;

import org.prgms.voucher.exception.WrongDiscountPercentException;

public class PercentDiscountVoucher implements Voucher {
    public static final long MIN_PERCENT = 1;
    public static final long MAX_PERCENT = 100;

    private final UUID voucherId;
    private final long discountPercent;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) throws WrongDiscountPercentException {
        validateDiscountPercent(discountPercent);
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    private void validateDiscountPercent(long discountPercent) throws WrongDiscountPercentException {
        if (MAX_PERCENT < discountPercent || discountPercent < MIN_PERCENT) {
            throw new WrongDiscountPercentException();
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * (1 - (discountPercent / 100.0)));
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return String.format("PercentDiscountVoucher : voucherId=%s, discountPercent=%d", voucherId, discountPercent);
    }
}
