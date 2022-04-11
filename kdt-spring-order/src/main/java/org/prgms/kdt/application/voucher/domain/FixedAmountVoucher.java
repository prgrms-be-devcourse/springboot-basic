package org.prgms.kdt.application.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private UUID voucherId;
    private final long amount = 2000L;

    public FixedAmountVoucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
