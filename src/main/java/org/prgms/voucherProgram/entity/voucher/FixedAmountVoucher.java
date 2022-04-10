package org.prgms.voucherProgram.entity.voucher;

import java.util.UUID;

import org.prgms.voucherProgram.exception.WrongDiscountAmountException;

public class FixedAmountVoucher implements Voucher {
    private static final int MIN_AMOUNT = 1;

    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) throws WrongDiscountAmountException {
        validateDiscountAmount(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmount(long discountAmount) throws WrongDiscountAmountException {
        if (isUnderMinAmount(discountAmount)) {
            throw new WrongDiscountAmountException();
        }
    }

    private boolean isUnderMinAmount(long discountAmount) {
        return discountAmount < MIN_AMOUNT;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d", VoucherType.FIXED_AMOUNT, voucherId, discountAmount);
    }
}
