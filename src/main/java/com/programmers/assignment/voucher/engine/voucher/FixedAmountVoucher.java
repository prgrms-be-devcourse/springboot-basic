package com.programmers.assignment.voucher.engine.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private static final long MIN_VOUCHER_AMOUNT = 0;

    public FixedAmountVoucher(UUID voucherId, String discountWay, long discountValue, UUID customerId) {
        super(voucherId, discountWay, discountValue, customerId);
        validateDiscountValue(discountValue);
    }

    private static void validateDiscountValue(long discountValue) {
        if (discountValue <=MIN_VOUCHER_AMOUNT) {
            logger.error("illegal fixed amount input : " + discountValue);
            throw new IllegalArgumentException("Amount should be over zero");
        }
        if (discountValue > MAX_VOUCHER_AMOUNT) {
            logger.error("illegal fixed amount input : " + discountValue);
            throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        }
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - discountValue;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("voucher type -> Fixed, voucherId -> {0}, Discount Amount -> {1}", voucherId, discountValue);
    }
}

