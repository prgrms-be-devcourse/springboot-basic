package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final static long MIN_VOUCHER_PERCENT = 0;
    private final static long MAX_VOUCHER_PERCENT = 100;
    private final static int PERCENTAGE = 100;

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
        return beforeDiscount * (percent / PERCENTAGE);
    }

    @Override
    public String toString() {
        return "Percent " +
                voucherId + " " +
                percent;
    }
    
}
