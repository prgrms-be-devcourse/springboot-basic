package org.prgrms.dev.voucher.domain;

import org.prgrms.dev.voucher.exception.InvalidArgumentException;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final int MAX_AMOUNT = 10000;
    private static final int ZERO_AMOUNT = 0;

    private final UUID voucherId;
    private final long amount;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        validate(amount);

        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "fixed" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
