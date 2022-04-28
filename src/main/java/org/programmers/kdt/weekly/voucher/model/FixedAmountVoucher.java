package org.programmers.kdt.weekly.voucher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private long amount;
    private final LocalDateTime createdAt;
    private static final VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now().withNano(0);
    }

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
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

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getValue() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}