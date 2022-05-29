package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private long value;
    private final LocalDateTime createdAt;
    private final VoucherType type = VoucherType.FIXED;

    public FixedAmountVoucher(UUID id, long value, LocalDateTime createdAt) {
        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
    }

    public FixedAmountVoucher(long value) {
        this.id = UUID.randomUUID();
        this.value = value;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "id=" + id +
            ", amount=" + value +
            '}';
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - this.value;
    }

    @Override
    public VoucherType getVoucherType() {
        return this.type;
    }

    @Override
    public long getValue() {
        return this.value;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public VoucherType getType() {
        return type;
    }

}
