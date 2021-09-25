package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final VoucherType type;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be more than 0");
        this.voucherId = voucherId;
        this.amount = amount;
        this.type = VoucherType.FIXED;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return amount;
    }

    @Override
    public Enum<VoucherType> getType() {
        return type;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }
}
