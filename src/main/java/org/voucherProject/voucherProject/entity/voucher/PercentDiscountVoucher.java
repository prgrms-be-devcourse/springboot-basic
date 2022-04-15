package org.voucherProject.voucherProject.entity.voucher;

import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString(exclude = {"voucherId", "voucherType"})
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    @Nullable
    private final VoucherType voucherType = VoucherType.PERCENT;
    @Nullable
    private VoucherStatus voucherStatus;
    private final LocalDateTime createdAt;

    private final int MIN_DISCOUNT_PERCENT = 0;
    private final int MAX_DISCOUNT_PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validatePercent(percent);
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherStatus = VoucherStatus.VALID;
        this.createdAt = LocalDateTime.now();
    }

    private void validatePercent(long percent) {
        if (percent < MIN_DISCOUNT_PERCENT || percent > MAX_DISCOUNT_PERCENT || percent == 0) {
            throw new IllegalArgumentException();
        }
    }

    public PercentDiscountVoucher(UUID voucherId, long percent, @Nullable VoucherStatus voucherStatus, LocalDateTime createdAt) {
        validatePercent(percent);
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherStatus = voucherStatus;
        this.createdAt = createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (1 - percent / 100);
    }

    @Override
    public long getHowMuch() {
        return this.percent;
    }

    @Override
    public VoucherStatus getVoucherStatus() {
        return this.voucherStatus;
    }

    @Override
    public void useVoucher() {
        this.voucherStatus = VoucherStatus.EXPIRED;
    }

    @Override
    public void cancelVoucher() {
        this.voucherStatus = VoucherStatus.VALID;
    }
}
