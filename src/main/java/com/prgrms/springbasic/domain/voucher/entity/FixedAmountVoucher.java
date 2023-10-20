package com.prgrms.springbasic.domain.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    private FixedAmountVoucher(String discountType, long discountValue) {
        this.voucherId = UUID.randomUUID();
        this.discountType = DiscountType.find(discountType);
        this.discountValue = discountValue;
    }

    public static FixedAmountVoucher create(String discountType, long discountValue) {
        if(discountValue < 0) {
            logger.warn("discount value should be positive. Inserted discount value : {}", discountValue);
            throw new IllegalArgumentException("discount value should be positive");
        }
        return new FixedAmountVoucher(discountType, discountValue);
    }

    public FixedAmountVoucher(UUID voucherId, String discountType, long discountValue) {
        this.voucherId = voucherId;
        this.discountType = DiscountType.find(discountType);
        this.discountValue = discountValue;
    }

    @Override
    long discount(long beforeDiscount) {
        long discountAmount = beforeDiscount - discountValue;
        return (discountAmount < 0) ? 0 : discountAmount;
    }
}
