package com.prgms.vouchermanager.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID id;

    private final long percent;

    private final VoucherType type;

    private LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID id, long percent, LocalDateTime createdAt) {
        this.id = id;
        this.percent = percent;
        this.createdAt = createdAt;
        type = VoucherType.PERCENT_DISCOUNT;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long getDiscountValue() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "id=" + id +
                ", percent=" + percent +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}
