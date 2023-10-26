package com.programmers.vouchermanagement.voucher.domain;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {
    FIXED("Fixed Amount"),
    PERCENT("Percent");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final String INVALID_VOUCHER_TYPE_MESSAGE =
            "Voucher type should be either fixed amount or percent discount voucher.";

    private final String typeName;

    VoucherType(String typeName) {
        this.typeName = typeName;
    }

    public static VoucherType findVoucherType(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error(INVALID_VOUCHER_TYPE_MESSAGE);
                    return new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE);
                });
    }

    private boolean isMatching(String input) {
        return input.equalsIgnoreCase(this.name());
    }

    public boolean isPercent() {
        return this == PERCENT;
    }

    public String displayTypeName() {
        return typeName;
    }
}
