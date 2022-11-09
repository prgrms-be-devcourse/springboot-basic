package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final int MAX_PERCENT_LIMIT = 100;
    private final int MIN_PERCENT_LIMIT = 1;
    private final double percent;

    public PercentDiscountVoucher(UUID voucherId, double percent) {
        if (percent > MAX_PERCENT_LIMIT) {
            throw new AmountException("퍼센트는 100 이하여야 합니다.");
        }
        if (percent <= MIN_PERCENT_LIMIT) {
            throw new AmountException("퍼센트는 1 이상이어야 합니다.");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public double getAmount() {
        return percent;
    }

    @Override
    public double discount(double beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}

