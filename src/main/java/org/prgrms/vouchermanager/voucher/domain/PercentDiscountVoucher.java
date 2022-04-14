package org.prgrms.vouchermanager.voucher.domain;

public class PercentDiscountVoucher extends AbstractVoucher {

    private final long percent;

    public PercentDiscountVoucher(long percent) {
        super(VoucherType.PERCENT);

        if (percent <= 0 || percent > 100) throw new IllegalArgumentException("Percent should be between 0 and 100");

        this.percent = percent;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return Math.round(beforeDiscount * (100 - percent) / 100.0);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + getVoucherId() +
                ", voucherType=" + getVoucherType() +
                ", percent=" + percent +
                '}';
    }
}
