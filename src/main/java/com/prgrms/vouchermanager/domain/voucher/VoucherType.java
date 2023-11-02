package com.prgrms.vouchermanager.domain.voucher;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String label;
    private static final Map<String, VoucherType> BY_LABEL =
            Stream.of(values()).collect(Collectors.toMap(VoucherType::getLabel, type -> type));

    VoucherType(String label) {
        this.label = label;
    }

    public static VoucherType of(String label) {
        return BY_LABEL.get(label);
    }
}
