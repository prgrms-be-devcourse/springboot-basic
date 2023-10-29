package com.prgrms.springbasic.domain.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private static final long MIN_PERCENT = 0L;
    private static final long MAX_PERCENT = 100L;

    public PercentDiscountVoucher(UUID voucherId, DiscountType discountType, long discountValue) {
        checkValidation(discountValue);
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    @Override
    public void update(long updateDiscountValue) {
        checkValidation(updateDiscountValue);
        this.discountValue = updateDiscountValue;
    }

    private static void checkValidation(long discountValue) {
        if (discountValue < MIN_PERCENT || discountValue > MAX_PERCENT) {
            logger.error("The percentage should be between 1 and 100. Inserted discount value : {}", discountValue);
            throw new IllegalArgumentException("The percentage should be between 1 and 100.");
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (discountValue / 100);
    }
}
