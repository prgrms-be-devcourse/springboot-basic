package org.prgrms.assignment.voucher.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final UUID voucherId;
    private final long percent;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
        checkValid(percent);
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long getBenefit() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    private void checkValid(long percent) {
        if(percent < 0 || percent >= 100) {
            throw new IllegalArgumentException("percent should be higher than 0 or lower than 100");
        }
    }
}
