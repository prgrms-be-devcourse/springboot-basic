package org.prgrms.kdt.voucher;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String type;
    private static final Map<String, VoucherType> voucherTypeMap = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(VoucherType::getType, Function.identity()))
    );

    public String getType() {
        return type;
    }

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType of(String type) {
        Objects.requireNonNull(type, "Command should be not null.");

        return Optional.ofNullable(voucherTypeMap.get(type.toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Please enter among " + names() + "." + System.lineSeparator()));

    }

    private static String names() {
        return Arrays.stream(VoucherType.values())
                .map(VoucherType::getType)
                .collect(Collectors.joining(", "));
    }
}
