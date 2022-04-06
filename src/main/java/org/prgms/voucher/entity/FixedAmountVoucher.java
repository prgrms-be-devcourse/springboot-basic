package org.prgms.voucher.entity;

import java.util.UUID;

import org.prgms.voucher.exception.ZeroDiscountAmountException;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) throws ZeroDiscountAmountException {
        validateDiscountAmount(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmount(long discountAmount) throws ZeroDiscountAmountException {
        if (discountAmount == 0) {
            throw new ZeroDiscountAmountException();
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
