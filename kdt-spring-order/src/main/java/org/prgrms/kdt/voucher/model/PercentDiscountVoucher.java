package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if(percent > 100) throw new IllegalArgumentException("percent가 100% 이상일 수 없음");
        if(percent < 0) throw new IllegalArgumentException("percent가 음수일 수 없음");
        this.voucherId = voucherId;
        this.percent = percent;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherValue() {
        return percent;
    }

    @Override
    public Enum<VoucherType> getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (percent / 100);
    }

}
