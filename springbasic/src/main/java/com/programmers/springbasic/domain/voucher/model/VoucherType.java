package com.programmers.springbasic.domain.voucher.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VoucherType {
    FIXED_AMOUNT_VOUCHER, PERCENT_DISCOUNT_VOUCHER;

    public static VoucherType of(String inputVoucherOption) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.name().equalsIgnoreCase(inputVoucherOption))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong Option!"));
    }
}
