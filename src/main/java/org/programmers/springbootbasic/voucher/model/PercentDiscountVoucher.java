package org.programmers.springbootbasic.voucher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private long percent;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
        validatePercent(percent);
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
    }

    private void validatePercent(long percent) {
        if (percent < 0 || percent > 99) {
            throw new IllegalArgumentException("할인율은 0 초과 100 이하여야 합니다.");
        }
    }

    @Override
    public String toString() {
        return "PERCENT" +
                " voucherId: " + voucherId +
                " percent: " + percent +
                " createdAt: " + createdAt;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - percent) / 100;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long getValue() {
        return percent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public void changeValue(long value) {
        this.percent = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return voucherId.equals(that.voucherId);
    }


    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}
