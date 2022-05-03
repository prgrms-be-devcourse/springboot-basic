package org.programmers.kdt.weekly.voucher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private long value;
    private final LocalDateTime createdAt;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, long value) {
        if (value <= 0) {
            throw new IllegalArgumentException();
        }

        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = LocalDateTime.now().withNano(0);
        this.voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
    }

    public FixedAmountVoucher(UUID voucherId, long value, LocalDateTime createdAt) {
        if (value <= 0) {
            throw new IllegalArgumentException();
        }

        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = createdAt;
        this.voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - value;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void changeValue(long value) {
        this.value = value;
    }

    @Override
    public String getVoucherType() {
        return "FixedAmountVoucher";
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Voucher Type: " + voucherType +
            ", voucherId: " + voucherId +
            ", amount: " + value + ", createdAt: " + createdAt;
    }

    @Override
    public String serializeVoucher() {
        return voucherId + "," + voucherType + "," + value + "," + createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedAmountVoucher)) {
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