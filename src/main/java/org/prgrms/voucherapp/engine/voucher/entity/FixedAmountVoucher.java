package org.prgrms.voucherapp.engine.voucher.entity;

import org.prgrms.voucherapp.global.enums.VoucherType;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountAmount;
    private static final String type = VoucherType.FIX.toString();
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long discountAmount, LocalDateTime createdAt) {
        this.discountAmount = discountAmount;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    @Override
    public long getAmount() {
        return discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getTypeName() {
        return type;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedPrice = beforeDiscount - discountAmount;
        return (discountedPrice < 0) ? 0 : discountedPrice;
    }


    @Override
    public String toString() {
        return String.format("TYPE : %7s, AMOUNT : %7s, ID : %36s", type, discountAmount, voucherId);
    }
}
