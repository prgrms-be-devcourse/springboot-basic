package com.example.voucherproject.voucher.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
@RequiredArgsConstructor
public enum VoucherType {
    ALL("ALL",(a,b)-> 0L),
    FIXED("FIXED", (originPrice, amount) -> originPrice - amount),
    PERCENT("PERCENT", (originPrice, amount) -> {
        double discountedPrice = originPrice * (1 - 0.01 * amount);
        // validation
        return (long) discountedPrice;
    });

    private final String name;
    private final BiFunction<Long, Long, Long> voucher;

    public Long discount(Long originPrice, Long amount){
        return voucher.apply(originPrice, amount);
    }

}
