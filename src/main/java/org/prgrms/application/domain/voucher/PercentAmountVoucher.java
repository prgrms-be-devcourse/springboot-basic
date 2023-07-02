package org.prgrms.application.domain.voucher;

public class PercentAmountVoucher extends Voucher {
    private static final double ZERO_PERCENT_DISCOUNT = 0;
    private static final double HUNDRED_PERCENT_DISCOUNT = 0;

    public PercentAmountVoucher(Long voucherId, double percent) {
        if (ZERO_PERCENT_DISCOUNT <= 0 || HUNDRED_PERCENT_DISCOUNT >= 100) throw new IllegalArgumentException("잘못된 입력 범위입니다.");
        this.voucherId = voucherId;
        this.amount = percent;
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public double discount(double beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }
}
