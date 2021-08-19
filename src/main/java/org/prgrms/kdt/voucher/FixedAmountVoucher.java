package org.prgrms.kdt.voucher;

import lombok.ToString;

import java.util.UUID;

@ToString
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getType() {
        return VoucherType.FixedAmountVoucher;
    }

    @Override
    public long getDiscount() {
        return amount;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
