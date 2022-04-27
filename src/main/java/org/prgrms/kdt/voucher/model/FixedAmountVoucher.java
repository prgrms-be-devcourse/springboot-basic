package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final VoucherType type = VoucherType.FIXED;

    private  FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static FixedAmountVoucher create(long amount) {
        return new FixedAmountVoucher(UUID.randomUUID(), amount);
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

}
