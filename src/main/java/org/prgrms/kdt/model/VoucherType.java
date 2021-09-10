package org.prgrms.kdt.model;

import java.util.function.Supplier;
import org.prgrms.kdt.model.discount.DiscountStrategy;
import org.prgrms.kdt.model.discount.FixedDiscountStrategy;
import org.prgrms.kdt.model.discount.PercentDiscountStrategy;

public enum VoucherType {
    FIX("1", FixedDiscountStrategy::new),
    PERCENT("2", PercentDiscountStrategy::new);

    private final String num;
    private final Supplier<DiscountStrategy> discountStrategyCreator;

    VoucherType(String value, Supplier<DiscountStrategy> discountStrategyCreator) {
        this.num = value;
        this.discountStrategyCreator = discountStrategyCreator;
    }

    public String getNum() {
        return num;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategyCreator.get();
    }

    @Override
    public String toString() {
        return "%s(%s)".formatted(super.toString(), this.getNum());
    }
}
