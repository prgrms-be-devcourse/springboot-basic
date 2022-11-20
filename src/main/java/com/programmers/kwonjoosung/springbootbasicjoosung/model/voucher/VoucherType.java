package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongVoucherTypeException;

import java.util.Objects;
import java.util.stream.Stream;

public enum VoucherType {

    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String TYPE;

    VoucherType(String type) {
        this.TYPE = type;
    }

    public static VoucherType of(String inputVoucherType) {
        return Stream.of(VoucherType.values())
                .filter(value -> Objects.equals(value.TYPE, inputVoucherType.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new WrongVoucherTypeException(inputVoucherType));
    }
}
