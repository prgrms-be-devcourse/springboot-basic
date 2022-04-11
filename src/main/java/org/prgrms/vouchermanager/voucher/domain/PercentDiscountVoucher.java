package org.prgrms.vouchermanager.voucher.domain;

public class PercentDiscountVoucher extends AbstractVoucher {

    private final long percent;

    public PercentDiscountVoucher(long percent) {
        super(VoucherType.PERCENT);
        if (percent <= 0 || percent > 100) throw new IllegalArgumentException("Percent should be between 0 and 100");
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        //TODO: 후에 출력 양식 정하면 StringBuilder나 Message Format으로 바꿀 것
        return "PercentDiscountVoucher{" +
                "voucherId+" + getVoucherId() +
                "voucherType" + getVoucherType() +
                "percent=" + percent +
                '}';
    }
}
