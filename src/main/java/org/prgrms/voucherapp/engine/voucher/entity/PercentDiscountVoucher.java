package org.prgrms.voucherapp.engine.voucher.entity;

import org.prgrms.voucherapp.global.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percentAmount;
    private static final String type = VoucherType.PERCENT.toString();
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percentAmount, LocalDateTime createdAt) {
        this.percentAmount = percentAmount;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    @Override
    public long getAmount() {
        return percentAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedPrice = (long) (beforeDiscount * (percentAmount / 100.0));
        return (discountedPrice < 0) ? 0 : discountedPrice;
    }

    @Override
    public String toString() {
        return String.format("TYPE : %7s, AMOUNT : %6s%%, ID : %36s", type, percentAmount, voucherId);
    }

}
