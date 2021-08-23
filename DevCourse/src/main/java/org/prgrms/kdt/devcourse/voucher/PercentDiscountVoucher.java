package org.prgrms.kdt.devcourse.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType = VoucherType.PERCENT;
    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public long getVoucherAmount() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return  (long) (beforeDiscount * ((100-percent)/100.0));
    }
}
