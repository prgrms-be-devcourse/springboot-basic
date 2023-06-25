package org.prgrms.kdt.voucher;

import java.util.UUID;


public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
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
    public long discountAppliedPrice(long beforeDiscount) {
        return beforeDiscount * (100 - percent) / 100;
    }

    @Override
    public String toString() {
        return String.format("%-25s id: %s discountPercent: %d", "[PercentDiscountVoucher]", voucherId.toString(), percent);
    }

}
