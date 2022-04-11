package org.prgrms.part1.engine;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;

    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public Long getValue() {
        return percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PercentDiscount;
    }
}
