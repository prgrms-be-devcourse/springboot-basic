package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final static long MIN_VOUCHER_PERCENT = 0;
    private final static long MAX_VOUCHER_PERCENT = 100;
    private final static long PERCENTAGE = 100;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validate(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public void validate(long percent) {
        if (percent < MIN_VOUCHER_PERCENT) {
            throw new InvalidArgumentException(ErrorMessage.MORE_THAN_MIN_VOUCHER_PERCENT);
        }

        if (percent == MIN_VOUCHER_PERCENT) {
            throw new InvalidArgumentException(ErrorMessage.NOT_BE_ZERO_VOUCHER_PERCENT);
        }

        if (percent > MAX_VOUCHER_PERCENT) {
            throw new InvalidArgumentException(ErrorMessage.LESS_THAN_MAX_VOUCHER_PERCENT);
        }
    }

    @Override
    public UUID voucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountAmount = beforeDiscount * (percent / PERCENTAGE);
        if (discountAmount < 0) {
            throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
        }
        return discountAmount;
    }

    @Override
    public String toString() {
        return "Percent " +
                voucherId + " " +
                percent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return percent == that.percent && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, percent);
    }

    @Override
    public long value() {
        return percent;
    }
}
