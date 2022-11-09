package org.prgrms.kdt.voucher;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
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

    public static VoucherType findVoucherType(String type) {
        validateTypeNullSafe(type);

        return Optional.ofNullable(voucherTypeMap.get(type.toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Please enter among " + names() + "." + System.lineSeparator()));

    }

    private static String names() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(
                EnumSet.allOf(VoucherType.class).stream()
                        .map(v -> v.type)
                        .collect(Collectors.joining(", "))
        );

        return stringBuffer.toString();
    }

    private static void validateTypeNullSafe(String type) {
        if (type == null) {
            throw new NullPointerException("type cannot be null");
        }
    }
}
