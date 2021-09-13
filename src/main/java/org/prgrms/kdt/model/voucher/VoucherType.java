package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.model.discount.DiscountStrategy;
import org.prgrms.kdt.model.discount.FixedDiscountStrategy;
import org.prgrms.kdt.model.discount.PercentDiscountStrategy;

public enum VoucherType {
    FIX("1", new FixedDiscountStrategy()),
    PERCENT("2", new PercentDiscountStrategy());

    private final String num;
    private final DiscountStrategy discountStrategy;

    VoucherType(String value, DiscountStrategy discountStrategy) {
        this.num = value;
        this.discountStrategy = discountStrategy;
    }


    public String getNum() {
        return num;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    @Override
    public String toString() {
        return "%s(%s)".formatted(super.toString(), this.getNum());
    }
}
