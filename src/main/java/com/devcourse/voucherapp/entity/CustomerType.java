package com.devcourse.voucherapp.entity;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum CustomerType {
    NORMAL(1, "일반 고객"),
    BLACK(2, "블랙리스트 고객");

    private static final Map<Integer, CustomerType> CUSTOMER_TYPES = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(CustomerType::getNumber, Function.identity())));

    private final int number;
    private final String name;

    CustomerType(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static CustomerType from(int customerTypeNumber) {
        return CUSTOMER_TYPES.get(customerTypeNumber);
    }

    @Override
    public String toString() {
        return name;
    }
}
