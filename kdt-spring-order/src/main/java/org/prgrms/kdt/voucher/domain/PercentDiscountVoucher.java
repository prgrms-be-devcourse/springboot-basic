package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    // -------------------------------------- ( 작성한 부분 ) --------------------------------------

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
        return VoucherType.percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

}
