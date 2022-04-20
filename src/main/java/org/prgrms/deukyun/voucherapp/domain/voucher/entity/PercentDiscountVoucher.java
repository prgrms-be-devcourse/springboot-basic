package org.prgrms.deukyun.voucherapp.domain.voucher.entity;

import java.text.MessageFormat;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정률 할인 바우처
 */
public class PercentDiscountVoucher implements Voucher {

    private static final int MIN_DISCOUNT_PERCENT = 0;
    private static final int MAX_DISCOUNT_PERCENT = 100;
    private final long percent;
    private UUID id;

    public PercentDiscountVoucher(long percent) {
        checkArgument(percent >= MIN_DISCOUNT_PERCENT && percent <= MAX_DISCOUNT_PERCENT,
                MessageFormat.format("percent must be between {1} and {2} inclusive.", MIN_DISCOUNT_PERCENT, MAX_DISCOUNT_PERCENT));

        this.percent = percent;
    }

    public long getPercent() {
        return percent;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice * (100 - percent) / 100;
    }

    @Override
    public String toDisplayString() {
        return new StringBuilder("[Percent Discount Voucher]     ")
                .append(" id : ").append(id.toString(), 0, 8)
                .append(", percent : ").append(percent)
                .toString();
    }
}
