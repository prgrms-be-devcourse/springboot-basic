package org.programmers.kdtspring.entity.voucher;

public class PercentDiscountVoucher extends Voucher {

    private static final int HUNDRED = 100;
    private final int percent;
    private final String voucherType;

    public PercentDiscountVoucher(Long voucherId, int percent, String voucherType) {
        super(voucherId);
        this.percent = percent;
        this.voucherType = voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / HUNDRED);
    }

    @Override
    public int getDiscount() {
        return this.percent;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }
}
