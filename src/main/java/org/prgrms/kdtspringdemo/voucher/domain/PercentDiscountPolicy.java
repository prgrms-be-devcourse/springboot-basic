package org.prgrms.kdtspringdemo.voucher.domain;

public class PercentDiscountPolicy implements VoucherPolicy {
    private final long percent;

    public PercentDiscountPolicy(long percent) {
        if(percent <= 0 || percent > 100) {
            throw new RuntimeException("할인률은 1~100까지만 적용 가능합니다.");
        }
        this.percent = percent;
    }
    public long getAmount() {
        return this.percent;
    }

    @Override
    public String getVoucherType() {
        return "percentdiscount";
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (1 - percent/100);
    }
}
