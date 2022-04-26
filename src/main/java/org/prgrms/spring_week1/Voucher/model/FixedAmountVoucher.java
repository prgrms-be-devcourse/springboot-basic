package org.prgrms.spring_week1.Voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private UUID voucherId;
    private long amount;
    private VoucherStatus voucherStatus = VoucherStatus.VALID;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public void setVoucherStatus(VoucherStatus voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "/ amount=" + amount +
            "/ voucherStatus=" + voucherStatus +
            '}';
    }
}
