package org.prgrms.java.domain.voucher;

import org.prgrms.java.exception.VoucherException;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private static final long MAX_AMOUNT = 100L;
    private static final long MIN_AMOUNT = 0L;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
        if (amount <= MIN_AMOUNT) throw new VoucherException("Voucher discount percent should be positive.");
        if (amount > MAX_AMOUNT) throw new VoucherException("Voucher discount percent cannot be bigger than 100.");
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }

    @Override
    public String toString() {
        return String.format("%s, %d%%", voucherId, amount);
    }
}
