package org.prgrms.kdt.domain.voucher;

import java.util.UUID;

public class PrecentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PrecentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent/100);
    }
}
