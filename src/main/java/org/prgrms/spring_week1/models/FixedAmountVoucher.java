package org.prgrms.spring_week1.models;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private UUID vocherId;
    private long amount;
    private VoucherStatus voucherStatus = VoucherStatus.VALID;

    public FixedAmountVoucher(UUID vocherId, long amount) {
        this.vocherId = vocherId;
        this.amount = amount;
    }

    public void setVoucherStatus(VoucherStatus voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount-amount;
    }
}
