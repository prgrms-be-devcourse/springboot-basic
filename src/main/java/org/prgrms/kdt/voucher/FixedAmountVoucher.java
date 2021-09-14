package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0) {
            String errorMsg = "Amount should be positive";
            logger.error(errorMsg);
            throw new IllegalArgumentException("Amount should be positive");
        }
        if (amount == 0) {
            String errorMsg ="Amount should not be zero";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        if (amount > MAX_VOUCHER_AMOUNT) {
            String errorMsg = String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT);
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher" +
                "," + voucherId +
                "," + amount;
    }
}