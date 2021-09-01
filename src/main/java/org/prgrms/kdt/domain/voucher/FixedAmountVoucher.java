package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.exception.ValidationException;

import java.util.UUID;

import static org.prgrms.kdt.exception.Message.*;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validate(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    private void validate(long amount) {
        if (amount < 0) throw new ValidationException(NEGATIVE_AMOUNT_MESSAGE);
        if (amount == 0) throw new ValidationException(ZERO_AMOUNT_MESSAGE);
        if (amount > MAX_VOUCHER_AMOUNT) throw new ValidationException(MAXIMUM_AMOUNT_MESSAGE);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return "voucherId = " + voucherId +
                ", amount = " + amount +
                "원";
    }
}
