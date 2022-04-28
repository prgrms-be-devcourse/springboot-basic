package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import lombok.Getter;

import java.text.MessageFormat;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정률 할인 바우처
 */
@Getter
public class PercentDiscountVoucher extends Voucher {

    /**
     * 할인율
     */
    private final long percent;

    private static final int MIN_DISCOUNT_PERCENT = 0;
    private static final int MAX_DISCOUNT_PERCENT = 100;

    public PercentDiscountVoucher(long percent) {
        this(UUID.randomUUID(), percent);
    }

    public PercentDiscountVoucher(UUID id, long percent){
        super(id);
        checkArgument(percent >= MIN_DISCOUNT_PERCENT && percent <= MAX_DISCOUNT_PERCENT,
                MessageFormat.format("percent must be between {1} and {2} inclusive.", MIN_DISCOUNT_PERCENT, MAX_DISCOUNT_PERCENT));

        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice * (100 - percent) / 100;
    }

}
