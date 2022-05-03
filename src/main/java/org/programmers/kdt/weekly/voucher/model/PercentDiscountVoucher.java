package org.programmers.kdt.weekly.voucher.model;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private long value;
    private final LocalDateTime createdAt;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, long value) {
        if (value <= 0 || value >= 100) {
            throw new IllegalArgumentException();
        }

        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = LocalDateTime.now().withNano(0);
        voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
    }

    public PercentDiscountVoucher(UUID voucherId, long value, LocalDateTime createdAt) {
        if (value <= 0 || value >= 100) {
            throw new IllegalArgumentException();
        }

        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = createdAt;
        voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (value / 100);
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
    public String toString() {
        return "Voucher Type: " + voucherType +
            ", voucherId: " + voucherId +
            ", percent: " + value + "%, createdAt: " + createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getVoucherType() {
        return "PercentDiscountVoucher";
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
        if (!(o instanceof PercentDiscountVoucher)) {
            return false;
        }
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return Objects.equals(getVoucherId(), that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId());
    }
}