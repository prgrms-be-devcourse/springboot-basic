package com.pppp0722.vouchermanagement.voucher.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private final VoucherType type = VoucherType.FIXED_AMOUNT;
    private final LocalDateTime createdAt;
    private final UUID memberId;

    public FixedAmountVoucher(UUID voucherId, long amount, UUID memberId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.memberId = memberId;
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt,
        UUID memberId) {
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
}
