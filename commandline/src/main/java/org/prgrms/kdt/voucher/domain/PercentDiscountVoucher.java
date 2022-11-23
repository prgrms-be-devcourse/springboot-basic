package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.WrongRangeInputException;

import java.util.Objects;

public class PercentDiscountVoucher extends Voucher {

    private static final int MAX_PERCENT = 100;
    private static final int MIN_PERCENT = 0;

    private final String typeName;

    public PercentDiscountVoucher(long voucherId, long discountDegree) {
        super(voucherId, discountDegree);
        this.typeName = "PercentDiscountVoucher";
    }

    @Override
    void validateVoucher(long discountDegree) {
        if (discountDegree < MIN_PERCENT || discountDegree > MAX_PERCENT) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT_EXCEPTION.getMessage());
        }
    }

    @Override
    public Voucher changeDiscountDegree(long discountDegree) {
        return new PercentDiscountVoucher(getVoucherId(), discountDegree);
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + getVoucherId() + ", " +
                "typeName=" + typeName + ", " +
                "discountDegree=" + getDiscountDegree() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return Objects.equals(getTypeName(), that.getTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeName());
    }
}
