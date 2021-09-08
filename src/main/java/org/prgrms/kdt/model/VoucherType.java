package org.prgrms.kdt.model;

import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FIXED_AMOUNT(
        "1",
        value -> new FixedAmountVoucher(UUID.randomUUID(), value)),

    PERCENT(
        "2",
        value -> new PercentDiscountVoucher(UUID.randomUUID(), value));

    private final String num;
    private final Function<Long, Voucher> creator;

    VoucherType(String value, Function<Long, Voucher> creator) {
        this.num = value;
        this.creator = creator;
    }

    public String getNum() {
        return num;
    }

    public Voucher create(Long value) {
        return creator.apply(value);
    }

    @Override
    public String toString() {
        return "%s. %s".formatted(this.getNum(), super.toString());
    }
}
