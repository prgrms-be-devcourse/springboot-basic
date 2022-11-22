package com.programmers.assignment.voucher.engine.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, String discountWay, long discountValue, UUID customerId) {
        super(voucherId, discountWay, discountValue, customerId);
        validateDiscountValue(discountValue);
    }

    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    private static final long MAX_VOUCHER_PERCENT = 100;

    private static final long MIN_VOUCHER_PERCENT = 0;


    private static void validateDiscountValue(long discountValue) {
        if (discountValue <= MIN_VOUCHER_PERCENT) {
            logger.error("illegal percent discount input : " + discountValue);
            throw new IllegalArgumentException("Percent should be over zero");
        }
        if (discountValue > MAX_VOUCHER_PERCENT) {
            logger.error("illegal percent discount input : " + discountValue);
            throw new IllegalArgumentException(String.format("Percent should be less than %d", MAX_VOUCHER_PERCENT));
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (discountValue / 100);
    }

    @Override
    public String toString() {
        return MessageFormat.format("voucher type -> Percent, voucherId -> {0}, Discount Percentage -> {1}", voucherId, discountValue);
    }
}
