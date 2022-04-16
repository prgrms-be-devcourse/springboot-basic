package org.prgrms.deukyun.voucherapp.domain.voucher.entity;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정률 할인 바우처
 */
public class PercentDiscountVoucher implements Voucher {

    private static final int MIN_DISCOUNT_PERCENT = 0;
    private static final int MAC_DISCOUNT_PERCENT = 100;
    public final long percent;
    private UUID id;

    public PercentDiscountVoucher(long percent) {
        checkArgument(percent >= MIN_DISCOUNT_PERCENT && percent <= MAC_DISCOUNT_PERCENT, "amount must be between 0 and 100 inclusive.");

        this.percent = percent;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice * (100 - percent) / 100;
    }
}
