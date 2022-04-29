package com.pppp0722.vouchermanagement.voucher.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final long MIN_PERCENT = 1;
    private static final long MAX_PERCENT = 100;

    private final UUID voucherId;
    private long amount;
    private VoucherType type = VoucherType.PERCENT_DISCOUNT;
    private final LocalDateTime createdAt;
    private final UUID memberId;

    public PercentDiscountVoucher(UUID voucherId, long amount, LocalDateTime createdAt,
        UUID memberId) {
        if (amount < MIN_PERCENT || amount > MAX_PERCENT) {
            throw new IllegalArgumentException("Percent must be between 1 and 100!");
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
        return "PercentDiscountVoucher{" + "voucherId=" + voucherId
            + ", amount=" + amount
            + ", type=" + type
            + ", createdAt=" + createdAt
            + ", memberId=" + memberId
            + '}';
    }

    @Override
    public void setType(VoucherType type) {
        this.type = type;
    }

    @Override
    public void setAmount(long amount) {
        this.amount = amount;
    }
}
