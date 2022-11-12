package org.prgrms.kdt.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.WrongRangeInputException;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final int MAX_PERCENT = 100;
    private static final int ZERO = 0;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, String percent) {
        validate(percent);
        this.voucherId = voucherId;
        this.percent = Long.parseLong(percent);
    }

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public void validate(String discountDegree) {
        Voucher.isNumeric(discountDegree);

        Long longDiscountValue = Long.parseLong(discountDegree);

        if (!(longDiscountValue > ZERO && longDiscountValue < MAX_PERCENT)) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT.getMessage());
        }
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
