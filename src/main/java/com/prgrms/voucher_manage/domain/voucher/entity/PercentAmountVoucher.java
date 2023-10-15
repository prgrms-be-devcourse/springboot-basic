package com.prgrms.voucher_manage.domain.voucher.entity;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PercentAmountVoucher implements Voucher {
    private static final Long MIN_DISCOUNT_PERCENT = 0L;
    private static final Long MAX_DISCOUNT_PERCENT = 100L;
    private final UUID voucherId;
    private final Long discountPercent;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscountAmount() {
        return discountPercent;
    }

    public boolean isValidDiscountPercent() {
        return (this.discountPercent >= MIN_DISCOUNT_PERCENT) && (this.discountPercent <= MAX_DISCOUNT_PERCENT);
    }
}
