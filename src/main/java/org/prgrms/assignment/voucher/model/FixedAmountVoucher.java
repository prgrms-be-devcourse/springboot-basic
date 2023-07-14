package org.prgrms.assignment.voucher.model;

import org.prgrms.assignment.voucher.exception.ErrorCode;
import org.prgrms.assignment.voucher.exception.GlobalCustomException;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final int MIN_VOUCHER_AMOUNT = 1;
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private static final String AMOUNT_ERROR_MESSAGE = "Amount out of range";
    private static final String EXPIRE_DATE_ERROR_MESSAGE = "Expiredate out of range";

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
        if (amount < MIN_VOUCHER_AMOUNT || amount > MAX_VOUCHER_AMOUNT) {
            throw new GlobalCustomException(ErrorCode.AMOUNT_ERROR);
        }
    }
}
