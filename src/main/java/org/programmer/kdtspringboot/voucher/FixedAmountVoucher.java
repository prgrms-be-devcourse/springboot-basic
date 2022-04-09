package org.programmer.kdtspringboot.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID voucherId;
    private final Long amount;

    public FixedAmountVoucher(UUID voucherId, Long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getValue() {
        return amount;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
