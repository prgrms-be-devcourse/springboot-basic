package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.exception.ValidationException;

import java.util.UUID;

import static org.prgrms.kdt.exception.Message.*;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 100;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validate(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private void validate(long percent) {
        if (percent < 0) throw new ValidationException(NEGATIVE_AMOUNT_MESSAGE);
        if (percent == 0) throw new ValidationException(ZERO_AMOUNT_MESSAGE);
        if (percent > MAX_VOUCHER_AMOUNT) throw new ValidationException(MAXIMUM_AMOUNT_MESSAGE);
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - percent / (double) 100));
    }

    @Override
    public String toString() {
        return "voucherId = " + voucherId +
                ", percent = " + percent +
                "%";
    }
}
