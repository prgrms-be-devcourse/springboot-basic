package org.prgms.kdt.application.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private UUID voucherId;
    private final long percent = 10L;

    public PercentDiscountVoucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}
