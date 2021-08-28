package org.prgrms.kdt.devcourse.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType = VoucherType.PERCENT;
    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if(percent<0)    throw new IllegalArgumentException("Amount should be positive");
        if(percent==0)    throw new IllegalArgumentException("Amount should not be zero");
        if(percent>100)    throw new IllegalArgumentException("Amount should be less than 100");
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
