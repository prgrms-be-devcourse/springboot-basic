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
    public Long apply(Long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
