package org.prgrms.kdt.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.prgrms.kdt.voucher.domain.VoucherType.PERCENT;

public class PercentDiscountVoucher extends Voucher {

    private final String type = PERCENT.toString();

    public PercentDiscountVoucher(UUID voucherId, int amount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public PercentDiscountVoucher(UUID voucherId, int amount, LocalDateTime createdAt, UUID customerId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }

    @Override
    public long getAmount() {
        return 0;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public UUID getCustomerId() {
        return null;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "type='" + type + '\'' +
                ", voucherId=" + voucherId +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                ", customerId=" + customerId +
                '}';
    }
}
