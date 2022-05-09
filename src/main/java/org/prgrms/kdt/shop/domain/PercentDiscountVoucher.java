package org.prgrms.kdt.shop.domain;

import org.prgrms.kdt.shop.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private static final VoucherType type = VoucherType.PERCENT_DISCOUNT;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime localDateTime) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = localDateTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (int) beforeDiscount / percent;
    }

    @Override
    public long getAmount() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return type;
    }
}
