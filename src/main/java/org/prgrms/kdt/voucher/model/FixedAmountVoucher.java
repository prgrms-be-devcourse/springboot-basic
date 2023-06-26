package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final String voucherName;
    private static int voucherNum = 0;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherName = getClass().getName() + voucherNum++;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
