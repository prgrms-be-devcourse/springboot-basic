package org.prgrms.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private UUID voucherId;
    private long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
