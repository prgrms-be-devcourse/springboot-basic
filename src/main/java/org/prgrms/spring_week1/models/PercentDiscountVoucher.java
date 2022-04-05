package org.prgrms.spring_week1.models;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private UUID voucerId;
    private long percent;
    private VoucherStatus voucherStatus = VoucherStatus.VALID;

    public PercentDiscountVoucher(UUID voucerId, long percent) {
        this.voucerId = voucerId;
        this.percent = percent;
    }

    public void setVoucherStatus(VoucherStatus voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }
}
