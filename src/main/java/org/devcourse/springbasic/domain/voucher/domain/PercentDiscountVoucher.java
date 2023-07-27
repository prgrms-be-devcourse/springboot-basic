package org.devcourse.springbasic.domain.voucher.domain;

import org.devcourse.springbasic.global.exception.custom.OutOfLimitsDiscountRateException;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(Long percentDiscountRate) {
        super(UUID.randomUUID(), percentDiscountRate, VoucherType.PERCENT_DISCOUNT, LocalDateTime.now());
        this.validate();
    }

    public PercentDiscountVoucher(UUID voucherId, Long percentDiscountRate, LocalDateTime createdAt) {
        super(voucherId, percentDiscountRate, VoucherType.PERCENT_DISCOUNT, createdAt);
        this.validate();
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (getDiscountRate() / 100L);
    }

    @Override
    public void validate() {
        if(getDiscountRate() < 0 || getDiscountRate() > 100) {
            throw new OutOfLimitsDiscountRateException("할인 비율의 범위가 올바르지 않습니다.");
        }
    }
}
