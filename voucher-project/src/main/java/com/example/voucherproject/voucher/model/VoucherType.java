package com.example.voucherproject.voucher.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public enum VoucherType {
    ALL("ALL"),
    FIXED("FIXED", (originPrice, amount) -> originPrice - amount),
    PERCENT("PERCENT", (originPrice, amount) -> {
        double discountedPrice = originPrice * (1 - 0.01 * amount);
        // validation
        return (long) discountedPrice;
    });

    private final String name;
    private BiFunction<Long, Long, Long> voucher;
    public Long discount(Long originPrice, Long amount){
        return voucher.apply(originPrice, amount);
    }

    public boolean isNotValid(long amount){
        if(this.name.equals("FIXED")){
            if(amount<0 || amount>10000) return true;
        }
        if(this.name.equals("PERCENT")){
            if(amount<0 || amount > 100) return true;
        }
        return false;
    }
}

