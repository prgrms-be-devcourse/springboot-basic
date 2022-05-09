package org.prgrms.kdt.shop.domain;

import org.prgrms.kdt.shop.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private static final VoucherType type = VoucherType.FIXED_AMOUNT;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime localDateTime) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = localDateTime;
    }

    public LocalDateTime getCreatedAt( ) {
        return createdAt;
    }

    @Override
    public UUID getVoucherId( ) {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getAmount( ) {
        return amount;
    }

    @Override
    public VoucherType getVoucherType( ) {
        return type;
    }
}
