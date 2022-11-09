package org.prgrms.java.domain.voucher;

import org.prgrms.java.exception.VoucherException;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent <= 0) throw new VoucherException("Voucher percent should be positive.");
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
    public String toString() {
        return String.format("%s, %d%%", voucherId, percent);
    }
}
