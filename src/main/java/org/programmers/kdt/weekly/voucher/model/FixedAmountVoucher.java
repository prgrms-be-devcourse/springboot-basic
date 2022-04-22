package org.programmers.kdt.weekly.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private int amount;
    private final LocalDateTime createdAt;
    private static final VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;

    public FixedAmountVoucher(UUID voucherId, int amount, LocalDateTime createdAt) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }
    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public int getValue() {
        return amount;
    }

    @Override
    public void changeValue(int value) {
        this.amount = value;
    }

    @Override
    public String toString() {
        return "Voucher Type: " + voucherType +
            ", voucherId: " + voucherId +
            ", amount: " + amount + ", createdAt: " + createdAt;
    }

    @Override
    public String serializeVoucher() {
        return voucherId + "," + voucherType + "," + amount + "," + createdAt;
    }
}