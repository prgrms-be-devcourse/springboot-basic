package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private UUID voucherId;
    private final long percent;

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
}
