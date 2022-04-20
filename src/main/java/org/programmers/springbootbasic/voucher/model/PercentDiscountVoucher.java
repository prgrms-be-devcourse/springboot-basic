package org.programmers.springbootbasic.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
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
        return "PercentDiscountVoucher" +
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

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    public static Voucher createVoucher(VoucherDTO voucherDTO) {
        return new PercentDiscountVoucher(voucherDTO.getVoucherId(), voucherDTO.getValue(), voucherDTO.getCreatedAt());
    }
}
