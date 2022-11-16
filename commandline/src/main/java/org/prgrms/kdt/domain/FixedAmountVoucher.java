package org.prgrms.kdt.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.WrongRangeInputException;

import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final int MIN_AMOUNT = 0;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validate(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public void validate(long discountDegree) {
        if (discountDegree < MIN_AMOUNT) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT_EXCEPTION.getMessage());
        }
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return amount == that.amount && Objects.equals(getVoucherId(), that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId(), amount);
    }


}
