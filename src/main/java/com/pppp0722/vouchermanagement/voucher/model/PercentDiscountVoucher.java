package com.pppp0722.vouchermanagement.voucher.model;

import com.pppp0722.vouchermanagement.exception.InvalidAmountException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final long MIN_PERCENT = 1;
    private static final long MAX_PERCENT = 100;

    private final UUID voucherId;
    private final long amount;
    private final VoucherType type = VoucherType.PERCENT_DISCOUNT;
    private final LocalDateTime createdAt;
    private final UUID memberId;

    public PercentDiscountVoucher(UUID voucherId, long amount, LocalDateTime createdAt,
        UUID memberId) {
        if (amount < MIN_PERCENT || amount > MAX_PERCENT) {
            throw new InvalidAmountException("Percent must be between 1 and 100!");
        }

        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS);
        this.memberId = memberId;
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
    public VoucherType getType() {
        return type;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public UUID getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PercentDiscountVoucher{");
        sb.append("voucherId=").append(voucherId);
        sb.append(", amount=").append(amount);
        sb.append(", type=").append(type);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", memberId=").append(memberId);
        sb.append('}');
        return sb.toString();
    }
}
