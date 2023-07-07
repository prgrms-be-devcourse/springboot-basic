package com.programmers.vouchermanagement.voucher.domain;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DiscountType {

    FIX("fix", FixedAmountDiscountPolicy::new),
    PERCENT("percent", PercentDiscountPolicy::new);

    private final String name;
    private final Function<Integer, DiscountPolicy> voucherCreationFunction;
    private static final Map<String, DiscountType> DISCOUNT_TYPE_MAP;

    static {
        DISCOUNT_TYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
                        .collect(Collectors.toMap(DiscountType::getName, Function.identity())));
    }

    DiscountType(String name, Function<Integer, DiscountPolicy> voucherCreationFunction) {
        this.name = name;
        this.voucherCreationFunction = voucherCreationFunction;
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

    public DiscountPolicy createDiscountPolicy(int amount) {
        return voucherCreationFunction.apply(amount);
    }
}
