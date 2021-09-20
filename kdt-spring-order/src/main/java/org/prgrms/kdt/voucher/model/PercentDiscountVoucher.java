package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if(percent <= 0) throw new IllegalArgumentException("percent must be more than 0");
        if(percent > 100) throw new IllegalArgumentException("percent must be less than 100");
        this.voucherId = voucherId;
        this.percent = percent;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public Enum<VoucherType> getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * ((100-percent) / 100.0));
    }

}
