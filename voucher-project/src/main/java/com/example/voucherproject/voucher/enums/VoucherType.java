package com.example.voucherproject.voucher.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
public enum VoucherType {
    FIXED("FIXED", (originPrice, amount) -> originPrice - amount),
    PERCENT("PERCENT", (originPrice, amount) -> {
        double discountedPrice = originPrice * (1 - 0.01 * amount);
        return (long) discountedPrice;
    });

    private final String name;
    private final BiFunction<Long, Long, Long> voucher;

    VoucherType(String name, BiFunction<Long, Long, Long> voucher) {
        this.name = name;
        this.voucher = voucher;
    }
    public Long discount(Long originPrice, Long amount){
        return voucher.apply(originPrice, amount);
    }

}
