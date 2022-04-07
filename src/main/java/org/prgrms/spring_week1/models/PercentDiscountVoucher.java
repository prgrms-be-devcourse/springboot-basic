package org.prgrms.spring_week1.models;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private UUID voucherId;
    private long percent;
    private VoucherStatus voucherStatus = VoucherStatus.VALID;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public void setVoucherStatus(VoucherStatus voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
