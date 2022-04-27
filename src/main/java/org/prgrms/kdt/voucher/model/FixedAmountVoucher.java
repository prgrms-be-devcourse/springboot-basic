package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private final LocalDateTime createdAt;
    private final VoucherType type = VoucherType.FIXED;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public FixedAmountVoucher(long value) {
        this.voucherId = UUID.randomUUID();
        this.amount = value;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "voucherId=" + voucherId +
            ", amount=" + amount +
            '}';
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - this.amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return this.type;
    }

    @Override
    public long getValue() {
        return this.amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

}
