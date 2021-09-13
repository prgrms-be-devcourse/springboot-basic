package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.model.discount.DiscountPolicy;
import org.prgrms.kdt.model.discount.FixedDiscountPolicy;
import org.prgrms.kdt.model.discount.PercentDiscountPolicy;

public enum VoucherType {
    FIX("1", new FixedDiscountPolicy()),
    PERCENT("2", new PercentDiscountPolicy());

    private final String num;
    private final DiscountPolicy discountPolicy;

    VoucherType(String value, DiscountPolicy discountPolicy) {
        this.num = value;
        this.discountPolicy = discountPolicy;
    }


    public String getNum() {
        return num;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    @Override
    public String toString() {
        return "%s(%s)".formatted(super.toString(), this.getNum());
    }
}
