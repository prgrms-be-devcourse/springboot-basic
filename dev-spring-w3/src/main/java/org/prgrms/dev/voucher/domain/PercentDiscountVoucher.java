package org.prgrms.dev.voucher.domain;

import org.prgrms.dev.exception.InvalidArgumentException;
import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final int MAX_PERCENT = 100;
    private static final int ZERO_PERCENT = 0;
    private static final int PERCENTAGE = 100;
    private static final VoucherType voucherType = VoucherType.PERCENT;

    private final UUID voucherId;
    private final long percent;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
        validate(percent);

        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
    }

    private void validate(long percent) {
        validatePercentIsZero(percent);
        validatePercentIsNegative(percent);
        validatePercentOutOfMax(percent);
    }

    private void validatePercentIsZero(long percent) {
        if (percent == ZERO_PERCENT) {
            throw new InvalidArgumentException("할인율은 0이 아니여야 합니다.");
        }
    }

    private void validatePercentIsNegative(long percent) {
        if (percent < ZERO_PERCENT) {
            throw new InvalidArgumentException("할인율은 양수여야 합니다.");
        }
    }

    private void validatePercentOutOfMax(long percent) {
        if (percent > MAX_PERCENT) {
            throw new InvalidArgumentException(String.format("할인율은 %d보다 작아야 합니다.", MAX_PERCENT));
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountValue() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (beforeDiscount * percent / PERCENTAGE);
    }

    @Override
    public String toString() {
        return "percent{" +
            "voucherId=" + voucherId +
            ", percent=" + percent +
            ", createdAt=" + createdAt +
            '}';
    }
}
