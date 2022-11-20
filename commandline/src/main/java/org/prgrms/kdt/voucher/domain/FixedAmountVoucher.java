package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.WrongRangeInputException;

import java.util.Objects;

public class FixedAmountVoucher implements Voucher {
    private static final int MIN_AMOUNT = 0;

    private final long voucherId;
    private final String typeName;
    private final long amount;

    public FixedAmountVoucher(long voucherId, String typeName, long amount) {
        validate(amount);
        this.voucherId = voucherId;
        this.typeName = typeName;
        this.amount = amount;
    }

    @Override
    public long getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountDegree() {
        return amount;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public void validate(long discountDegree) {
        if (discountDegree < MIN_AMOUNT) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT_EXCEPTION.getMessage());
        }
    }

    @Override
    public Voucher changeDiscountDegree(long discountDegree) {
        return new FixedAmountVoucher(this.voucherId, this.typeName, discountDegree);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return getVoucherId() == that.getVoucherId() && amount == that.amount && Objects.equals(getTypeName(), that.getTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId(), getTypeName(), amount);
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", typeName=" + typeName +
                ", amount=" + amount +
                '}';
    }
}
