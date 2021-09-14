package org.prgms.order.voucher.entity;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final static VoucherIndexType type = VoucherIndexType.FIXED;
    private final long amount;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public VoucherIndexType getType() {
        return type;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }


    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    @Override
    public void initExpiry() {
        expiredAt = LocalDateTime.now().withNano(0);
    }

}
