package org.prgrms.dev.voucher.domain;

import org.prgrms.dev.voucher.exception.InvalidArgumentException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final int MAX_AMOUNT = 10000;
    private static final int ZERO_AMOUNT = 0;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validate(amount);

        this.voucherId = voucherId;
        this.amount = amount;
    }

    private void validate(long amount) {
        validateAmountIsZero(amount);
        validateAmountIsNegative(amount);
        validateAmountOutOfMax(amount);
    }

    private void validateAmountIsZero(long amount) {
        if (amount == ZERO_AMOUNT) {
            throw new InvalidArgumentException("Amount should not be zero");
        }
    }

    private void validateAmountIsNegative(long amount) {
        if (amount < ZERO_AMOUNT) {
            throw new InvalidArgumentException("Amount should be positive");
        }
    }

    private void validateAmountOutOfMax(long amount) {
        if (amount > MAX_AMOUNT) {
            throw new InvalidArgumentException(String.format("Amount should be less than %d ", MAX_AMOUNT));
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountValue() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "fixed:" +
                voucherId +
                ":" + amount;
    }
}
