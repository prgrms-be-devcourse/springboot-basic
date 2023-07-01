package com.programmers.vouchermanagement.voucher.domain;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DiscountType {

    FIX("fix"),
    PERCENT("percent");

    private final String name;
    private static final Map<String, DiscountType> DISCOUNT_TYPE_MAP;

    static {
        DISCOUNT_TYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
                        .collect(Collectors.toMap(DiscountType::getName, Function.identity())));
    }

    DiscountType(String name) {
        this.name = name;
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
}
