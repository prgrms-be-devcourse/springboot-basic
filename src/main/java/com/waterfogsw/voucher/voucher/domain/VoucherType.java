package com.waterfogsw.voucher.voucher.domain;

import java.util.function.Function;

public enum VoucherType {
    FIXED_AMOUNT((x) -> x > 0),
    PERCENT_DISCOUNT((x) -> x > 0 && x <= 100);

    VoucherType(Function<Integer, Boolean> validationFunc) {
        this.validationFunc = validationFunc;
    }

    private final Function<Integer, Boolean> validationFunc;

    public Boolean validate(int x) {
        return this.validationFunc.apply(x);
    }


}
