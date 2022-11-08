package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.exceptions.ExceptionMessageType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent > 100) throw new AmountException(ExceptionMessageType.AMOUNT_OVER_100.getMessage());
        if (percent <= 0) throw new AmountException(ExceptionMessageType.AMOUNT_UNDER_ZERO.getMessage());
        this.voucherId = voucherId;
        this.percent = percent;
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
        return beforeDiscount * (percent / 100);
    }
}

