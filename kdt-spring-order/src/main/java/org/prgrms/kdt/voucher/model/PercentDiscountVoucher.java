package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final VoucherType type;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
        if (percent <= 0) throw new IllegalArgumentException("percent must be more than 0");
        if (percent > 100) throw new IllegalArgumentException("percent must be less than 100");
        this.voucherId = voucherId;
        this.percent = percent;
        this.type = VoucherType.PERCENT;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return percent;
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
        return (long) (beforeDiscount * ((100 - percent) / 100.0));
    }
}
