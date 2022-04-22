package org.voucherProject.voucherProject.voucher.entity;

import lombok.*;
import org.springframework.lang.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString(exclude = {"voucherType"})
public class PercentDiscountVoucher extends Voucher {

    private final long percent;
    @Nullable
    private final VoucherType voucherType = VoucherType.PERCENT;

    private final int MIN_DISCOUNT_PERCENT = 0;
    private final int MAX_DISCOUNT_PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, long percent, UUID customerId) {
        super(voucherId,customerId);
        validatePercent(percent);
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, long percent, @Nullable VoucherStatus voucherStatus, LocalDateTime createdAt, UUID customerId) {
        super(voucherId,voucherStatus,createdAt,customerId);
        validatePercent(percent);
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - percent) / 100;
    }

    @Override
    public long getHowMuch() {
        return this.percent;
    }

    private void validatePercent(long percent) {
        if (percent < MIN_DISCOUNT_PERCENT || percent > MAX_DISCOUNT_PERCENT || percent == 0) {
            throw new IllegalArgumentException();
        }
    }
}
