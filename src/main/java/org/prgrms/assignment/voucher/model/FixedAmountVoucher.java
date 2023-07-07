package org.prgrms.assignment.voucher.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final int MIN_VOUCHER_AMOUNT = 0;
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private long amount;
    private LocalDateTime expireDate;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt, LocalDateTime expireDate) {
        checkValid(amount);
        this.expireDate = expireDate;
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long getBenefit() {
        return amount;
    }

    @Override
    public void setBenefit(long benefit) {
        this.amount = benefit;
    }

    @Override
    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        if(discountedAmount < MIN_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Wrong discount Amount!");
        }
        return discountedAmount;
    }

    private void checkValid(long amount) {
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
    }
}
