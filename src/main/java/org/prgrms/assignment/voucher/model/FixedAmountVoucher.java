package org.prgrms.assignment.voucher.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;
    private final String voucherName;
    private static int voucherNum = 0;
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final int MIN_VOUCHER_AMOUNT = 0;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < MIN_VOUCHER_AMOUNT) {
            logger.error("Amount should be positive");
            throw new IllegalArgumentException("Amount should be positive");
        }
        if (amount == MIN_VOUCHER_AMOUNT) {
            logger.error("Amount should not be zero");
            throw new IllegalArgumentException("Amount should not be zero");
        }
        if (amount > MAX_VOUCHER_AMOUNT) {
            logger.error("Amount should be less than " + MAX_VOUCHER_AMOUNT);
            throw new IllegalArgumentException("Amount should be less than " + MAX_VOUCHER_AMOUNT);
        }
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherName = getClass().getSimpleName() + voucherNum++;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        if(discountedAmount < 0) {
            throw new IllegalArgumentException("Wrong discount Amount!");
        }
        return discountedAmount;
    }

    @Override
    public long getBenefit() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public String getVoucherName() {
        return voucherName;
    }
}
