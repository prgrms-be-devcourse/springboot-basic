package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.OutOfRangeException;

import java.util.Objects;
import java.util.UUID;

public class FixedAmountDiscountVoucher implements Voucher {

    private static final long MIN_AMOUNT = 0L;
    private static final long MAX_AMOUNT = 100_000L;
    private final UUID voucherId;
    private final long discountAmount;
    private final VoucherType voucherType = VoucherType.FIXED;
    private static final String WRONG_RANGE_MESSAGE = "The discount amount is 0 won or more and 100,000 won or less.";

    public FixedAmountDiscountVoucher(long discountAmount) {
        this(UUID.randomUUID(), discountAmount);
    }

    public FixedAmountDiscountVoucher(UUID voucherId, long discountAmount) {
        checkRange(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    private void checkRange(long discountAmount) {
        if (MIN_AMOUNT >= discountAmount || discountAmount >= MAX_AMOUNT) {
            throw new OutOfRangeException(discountAmount,WRONG_RANGE_MESSAGE);
        }
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getDiscount() {
        return discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "VoucherType: " + voucherType +
                ", voucherId: " + voucherId +
                ", discountPercent: " + discountAmount + "$";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedAmountDiscountVoucher that)) return false;
        return discountAmount == that.discountAmount && voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountAmount);
    }

}
