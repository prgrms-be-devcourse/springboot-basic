package org.prgrms.kdt.configure;

import lombok.ToString;

import java.util.UUID;

@ToString
public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long discount;

    public FixedAmountVoucher(UUID voucherId, long discount) {
        this.voucherId = voucherId;
        this.discount = discount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discount;
    }
}
