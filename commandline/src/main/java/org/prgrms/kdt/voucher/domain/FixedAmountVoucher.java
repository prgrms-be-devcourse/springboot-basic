package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.voucher.WrongRangeInputException;

import java.util.Objects;

public class FixedAmountVoucher extends Voucher {

    private static final int MIN_AMOUNT = 0;

    private final String typeName;

    public FixedAmountVoucher(long voucherId, long discountDegree) {
        super(voucherId, discountDegree);
        this.typeName = "FixedAmountVoucher";
    }

    @Override
    void validateVoucher(long discountDegree) {
        if (discountDegree < MIN_AMOUNT) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT_EXCEPTION.getMessage());
        }
    }

    @Override
    public Voucher changeDiscountDegree(long discountDegree) {
        validateVoucher(discountDegree);
        return new FixedAmountVoucher(getVoucherId(), discountDegree);
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + getVoucherId() + ", " +
                "typeName=" + typeName + ", " +
                "discountDegree=" + getDiscountDegree() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return Objects.equals(getTypeName(), that.getTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeName());
    }
}
