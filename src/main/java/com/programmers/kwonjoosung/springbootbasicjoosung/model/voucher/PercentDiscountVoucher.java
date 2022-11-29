package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.OutOfRangeException;

import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final long MIN_PERCENT = 0L;
    private static final long MAX_PERCENT = 100L;
    private final UUID voucherId;
    private final long discountPercent;
    private final VoucherType voucherType = VoucherType.PERCENT;
    private static final String WRONG_RANGE_MESSAGE = "The discount percent is 0% or more and 100% or less.";

    public PercentDiscountVoucher(long discountPercent) {
        this(UUID.randomUUID(), discountPercent);
    }

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
        checkRange(discountPercent);
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    private void checkRange(long discountPercent) {
        if (MIN_PERCENT >= discountPercent || discountPercent >= MAX_PERCENT) {
            throw new OutOfRangeException(discountPercent, WRONG_RANGE_MESSAGE);
        }
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getDiscount() {
        return discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "VoucherType: " + voucherType +
                ", voucherId: " + voucherId +
                ", discountPercent: " + discountPercent + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PercentDiscountVoucher that)) return false;
        return discountPercent == that.discountPercent && voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountPercent);
    }
}
