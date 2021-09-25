package org.prgrms.dev.voucher.domain;

import org.prgrms.dev.exception.InvalidArgumentException;

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
            throw new InvalidArgumentException("할인금액은 0원이 아니여야 합니다.");
        }
    }

    private void validateAmountIsNegative(long amount) {
        if (amount < ZERO_AMOUNT) {
            throw new InvalidArgumentException("할인금액은 양수여야 합니다.");
        }
    }

    private void validateAmountOutOfMax(long amount) {
        if (amount > MAX_AMOUNT) {
            throw new InvalidArgumentException(String.format("할인금액은 %d보다 작아야 합니다.", MAX_AMOUNT));
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
