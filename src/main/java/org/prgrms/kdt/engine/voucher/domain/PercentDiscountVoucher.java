package org.prgrms.kdt.engine.voucher.domain;

import org.prgrms.kdt.engine.voucher.VoucherType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 100;
    private final UUID voucherId;
    private final long percent;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent <= 0) throw new IllegalArgumentException("percent should be positive");
        if (percent > MAX_VOUCHER_PERCENT) throw new IllegalArgumentException(String.format("percent should be less than %d", MAX_VOUCHER_PERCENT));
        this.voucherId = voucherId;
        this.percent = percent;
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
        if (percent <= 0) throw new IllegalArgumentException("percent should be positive");
        if (percent > MAX_VOUCHER_PERCENT) throw new IllegalArgumentException(String.format("percent should be less than %d", MAX_VOUCHER_PERCENT));
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
    }

    @Override
    public long discount(long beforeDiscount) { return (long)(beforeDiscount * ((100 - percent) / 100.0)); }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherRate() { return percent; }

    @Override
    public VoucherType getVoucherType() { return VoucherType.PERCENT; }

    @Override
    public LocalDateTime getVoucherCreatedAt() { return createdAt; }

    @Override
    public String toString() { return "PERCENT&" + voucherId + "&" + percent; }
}
