package com.programmers.vouchermanagement.voucher.domain;

import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DiscountType {

    FIX("fix", (amount, discountType) -> new Voucher(new FixedAmountDiscountPolicy(amount), discountType)),
    PERCENT("percent", (amount, discountType) -> new Voucher(new PercentDiscountPolicy(amount), discountType));

    private final String name;
    private final BiFunction<Integer, DiscountType, Voucher> function;
    private static final Map<String, DiscountType> DISCOUNT_TYPE_MAP;

    static {
        DISCOUNT_TYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
                        .collect(Collectors.toMap(DiscountType::getName, Function.identity())));
    }

    DiscountType(String name, BiFunction<Integer, DiscountType, Voucher> function) {
        this.name = name;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public static DiscountType from(String name) {
        if (DISCOUNT_TYPE_MAP.containsKey(name)) {
            return DISCOUNT_TYPE_MAP.get(name);
        }
        throw new IllegalArgumentException("This discount type does not exist.");
    }

    public Voucher createVoucher(int amount) {
        return function.apply(amount, this);
    }
}
