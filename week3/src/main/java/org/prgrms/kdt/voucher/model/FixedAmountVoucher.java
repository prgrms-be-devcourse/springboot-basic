package org.prgrms.kdt.voucher.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private UUID walletId;

    public FixedAmountVoucher(UUID voucherId, long amount, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", voucherType=" + voucherType +
                ", createdAt=" + createdAt +
                ", walletId=" + walletId +
                '}';
    }
}


