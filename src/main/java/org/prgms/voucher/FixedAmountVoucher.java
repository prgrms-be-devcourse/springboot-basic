package org.prgms.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final Long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(Long amount, UUID voucherId) {
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public Long discount(Long discountAmount) {
        return null;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
