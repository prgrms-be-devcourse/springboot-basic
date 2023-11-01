package com.prgrms.springbasic.domain.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    public FixedAmountVoucher(UUID voucherId, DiscountType discountType, long discountValue, LocalDateTime createdAt) {
        checkValidation(discountValue);
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.createdAt = createdAt;
    }

    @Override
    public void update(long updateDiscountValue) {
        checkValidation(updateDiscountValue);
        this.discountValue = updateDiscountValue;
    }

    private static void checkValidation(long discountValue) {
        if (discountValue < 0) {
            logger.warn("discount value should be positive. Inserted discount value : {}", discountValue);
            throw new IllegalArgumentException("discount value should be positive");
        }
    }

    @Override
    long discount(long beforeDiscount) {
        long discountAmount = beforeDiscount - discountValue;
        if (discountAmount < 0) {
            logger.warn("할인된 금액이 0보다 작습니다. discountAmount : {}", discountAmount);
            return 0;
        }
        return discountAmount;
    }
}
