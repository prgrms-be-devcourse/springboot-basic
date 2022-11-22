package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.WrongRangeInputException;

import java.util.Objects;

public class PercentDiscountVoucher implements Voucher {
    private static final int MAX_PERCENT = 100;
    private static final int MIN_PERCENT = 0;

    private final long voucherId;
    private final String typeName;
    private final long percent;

    public PercentDiscountVoucher(long voucherId, String typeName, long percent) {
        validateVoucher(percent);

        this.voucherId = voucherId;
        this.typeName = typeName;
        this.percent = percent;
    }

    @Override
    public long getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountDegree() {
        return this.percent;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public void validateVoucher(long discountDegree) {
        if (!(discountDegree >= MIN_PERCENT && discountDegree <= MAX_PERCENT)) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT_EXCEPTION.getMessage());
        }
    }

    @Override
    public Voucher changeDiscountDegree(long discountDegree) {
        validateVoucher(discountDegree);
        return new PercentDiscountVoucher(this.voucherId, this.typeName, discountDegree);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return getVoucherId() == that.getVoucherId() && percent == that.percent && Objects.equals(getTypeName(), that.getTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId(), getTypeName(), percent);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", typeName=" + typeName +
                ", percent=" + percent +
                '}';
    }
}
