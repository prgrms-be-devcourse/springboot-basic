package com.mountain.voucherApp.enums;

import com.mountain.voucherApp.voucher.FixedAmountVoucher;
import com.mountain.voucherApp.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.voucher.Voucher;

import java.util.function.Function;

public enum DiscountPolicy {
    FIXED(1, "고정 할인", (amount) -> new FixedAmountVoucher(amount)),
    PERCENT(2, "비율 할인", (amount) -> new PercentDiscountVoucher(amount));

    private final int ordinal;
    private final String description;
    private final Function<Long, Voucher> function;

    DiscountPolicy(int ordinal, String description, Function<Long, Voucher> function) {
        this.ordinal = ordinal;
        this.description = description;
        this.function = function;
    }

    public String getDescription() {
        return description;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public Voucher getVoucher(long amount) {
        return function.apply(amount);
    }
}

