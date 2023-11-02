package com.prgms.vouchermanager.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;

    private final long amount;

    private final VoucherType type;

    private LocalDateTime createdAt;

    public FixedAmountVoucher(UUID id, long amount, LocalDateTime createdAt) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        type = VoucherType.FIXED_AMOUNT;
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long getDiscountValue() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED_AMOUNT;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "id=" + id +
                ", amount=" + amount +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}
