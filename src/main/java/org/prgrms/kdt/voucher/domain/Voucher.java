package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    public UUID voucherId;
    public final long amount = 20;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public abstract String getVoucherType();

    public abstract long discount(Long beforeDiscount);
}
