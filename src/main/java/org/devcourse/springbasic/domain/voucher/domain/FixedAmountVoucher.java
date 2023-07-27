package org.devcourse.springbasic.domain.voucher.domain;

import org.devcourse.springbasic.global.exception.custom.OutOfLimitsDiscountRateException;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {


    public FixedAmountVoucher(Long fixedDiscountRate) {
        super(UUID.randomUUID(), fixedDiscountRate, VoucherType.FIXED_AMOUNT, LocalDateTime.now());
        this.validate();
    }

    public FixedAmountVoucher(UUID voucherId, long fixedDiscountRate, LocalDateTime createdAt) {
        super(voucherId, fixedDiscountRate, VoucherType.FIXED_AMOUNT, createdAt);
        this.validate();
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - getDiscountRate();
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public void validate() {
        if(getDiscountRate() < 0) {
            throw new OutOfLimitsDiscountRateException("할인 비율의 범위가 올바르지 않습니다.");
        }
    }
}