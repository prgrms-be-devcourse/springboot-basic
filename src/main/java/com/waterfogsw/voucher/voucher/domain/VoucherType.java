package com.waterfogsw.voucher.voucher.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VoucherType {
    FIXED_AMOUNT("FIXED_AMOUNT"),
    PERCENT_DISCOUNT("PERCENT_DISCOUNT");

    String name;

    @JsonCreator
    VoucherType(String name) {
        this.name = name;
    }
}
