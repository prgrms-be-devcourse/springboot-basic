package org.prgrms.kdt.voucher.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private UUID walletId;

    public PercentDiscountVoucher(UUID voucherId, long percent, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    @Override
    public long getAmount() {
        return this.percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                ", voucherType=" + voucherType +
                ", createdAt=" + createdAt +
                ", walletId=" + walletId +
                '}';
    }
}
