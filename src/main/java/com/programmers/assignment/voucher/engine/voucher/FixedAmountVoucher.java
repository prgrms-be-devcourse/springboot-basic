package com.programmers.assignment.voucher.engine.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0) {
            logger.error("illegal fixed amount input : " + amount);
            throw new IllegalArgumentException("Amount should be positive");
        }
        if (amount == 0) {
            logger.error("illegal fixed amount input : " + amount);
            throw new IllegalArgumentException("Amount should not be zero");
        }
        if (amount > MAX_VOUCHER_AMOUNT) {
            logger.error("illegal fixed amount input : " + amount);
            throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("voucher type -> Fixed, voucherId -> {0}, Discount Amount -> {1}", voucherId, amount);
    }
}
