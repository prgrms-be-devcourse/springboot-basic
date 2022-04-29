package com.pppp0722.vouchermanagement.voucher.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final long MIN_AMOUNT = 100;
    private final long MAX_AMOUNT = 1000000;

    private final UUID voucherId;
    private long amount;
    private VoucherType type = VoucherType.FIXED_AMOUNT;
    private final LocalDateTime createdAt;
    private final UUID memberId;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt,
        UUID memberId) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("Amount must be between 100 and 1000000!");
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
        return "FixedAmountVoucher{" + "voucherId=" + voucherId
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
