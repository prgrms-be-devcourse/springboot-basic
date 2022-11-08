package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER = 10000;
    private final UUID voucherId;
    private final long amount;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= 0) throw new AmountException("Should be positive");
        if (amount > MAX_VOUCHER) throw new AmountException("Should be less than %d".formatted(MAX_VOUCHER));
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }
}
