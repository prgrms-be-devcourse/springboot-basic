package org.prgrms.kdt.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.WrongRangeInputException;

import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final int MAX_PERCENT = 100;
    private static final int MIN_PERCENT = 0;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validate(percent);

        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public void validate(long discountDegree) {
        if (!(discountDegree > MIN_PERCENT && discountDegree < MAX_PERCENT)) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT_EXCEPTION.getMessage());
        }
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return percent == that.percent && Objects.equals(getVoucherId(), that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId(), percent);
    }
}
