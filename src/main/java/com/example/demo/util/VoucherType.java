package com.example.demo.util;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("fix"),
    PERCENT_DISCOUNT_VOUCHER("percent");


    private static final Map<String, VoucherType> VOUCHER_TYPE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(VoucherType::getVoucher, Function.identity())));

    private final String voucher;

    VoucherType(String voucher) {
        this.voucher = voucher;
    }

    public String getVoucher() {
        return voucher;
    }

    public static VoucherType find(String input) {
        if (VOUCHER_TYPE_MAP.containsKey(input)) {
            return VOUCHER_TYPE_MAP.get(input);
        }
        throw new IllegalArgumentException("잘 못된 명령어 입니다.");
    }
    
}
