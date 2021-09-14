package org.prgrms.kdt.engine.voucher.domain;

import org.prgrms.kdt.engine.voucher.VoucherType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount should be positive");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("amount should be less than %d", MAX_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        if (amount <= 0) throw new IllegalArgumentException("amount should be positive");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("amount should be less than %d", MAX_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountAmount = beforeDiscount - amount;
        return (discountAmount < 0) ? 0 : discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherRate() { return amount; }

    @Override
    public VoucherType getVoucherType() { return VoucherType.FIXED; }

    @Override
    public LocalDateTime getVoucherCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "FIXED&" + voucherId + "&" + amount;
    }
}
