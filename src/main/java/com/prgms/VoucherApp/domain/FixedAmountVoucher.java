package com.prgms.VoucherApp.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long fixedAmount;

    public FixedAmountVoucher(UUID voucherId, long fixedAmount) {
        this.voucherId = voucherId;
        this.fixedAmount = fixedAmount;
    }

    @Override
    public long discount(long beforeAmount) {
        if (isResultNegative(beforeAmount))
            return 0L;

        return beforeAmount - fixedAmount;
    }

    private boolean isResultNegative(long beforeAmount) {
        return beforeAmount - fixedAmount < 0;
    }
}
