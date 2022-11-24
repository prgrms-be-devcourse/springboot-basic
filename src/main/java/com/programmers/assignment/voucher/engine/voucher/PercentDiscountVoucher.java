package com.programmers.assignment.voucher.engine.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    private static final long MAX_VOUCHER_PERCENT = 100;
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < 0) {
            logger.error("illegal percent discount input : " + percent);
            throw new IllegalArgumentException("Amount should be positive");
        }
        if (percent == 0) {
            logger.error("illegal percent discount input : " + percent);
            throw new IllegalArgumentException("Amount should not be zero");
        }
        if (percent > MAX_VOUCHER_PERCENT) {
            logger.error("illegal percent discount input : " + percent);
            throw new IllegalArgumentException(String.format("Percent should be less than %d", MAX_VOUCHER_PERCENT));
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent/100);
    }

    @Override
    public String toString() {
        return MessageFormat.format("voucher type -> Percent, voucherId -> {0}, Discount Percentage -> {1}", voucherId, percent);
    }
}
