package com.programmers.vouchermanagement.voucher.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum DiscountType {

    FIX("1", "정액 할인", FixedAmountDiscountPolicy::new),
    PERCENT("2", "정률 할인", PercentDiscountPolicy::new);

    private final String number;
    private final String name;
    private final Function<Integer, DiscountPolicy> voucherCreationFunction;
    private static final Map<String, DiscountType> DISCOUNT_TYPE_MAP;

    static {
        DISCOUNT_TYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
                        .collect(Collectors.toMap(DiscountType::getNumber, Function.identity())));
    }

    DiscountType(String number, String name, Function<Integer, DiscountPolicy> voucherCreationFunction) {
        this.number = number;
        this.name = name;
        this.voucherCreationFunction = voucherCreationFunction;
    }

    public static DiscountType from(String number) {
        if (DISCOUNT_TYPE_MAP.containsKey(number)) {
            return DISCOUNT_TYPE_MAP.get(number);
        }
        throw new IllegalArgumentException("존재하지 않는 할인 유형 입니다.");
    }

    public DiscountPolicy createDiscountPolicy(int amount) {
        return voucherCreationFunction.apply(amount);
    }
}
