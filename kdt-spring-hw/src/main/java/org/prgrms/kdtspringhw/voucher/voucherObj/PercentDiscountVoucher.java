package org.prgrms.kdtspringhw.voucher.voucherObj;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
    }

    @Override
    public long getData() {
        return percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (percent / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }

    @Override
    public VoucherType getType() {
        return this.voucherType;
    }

}
