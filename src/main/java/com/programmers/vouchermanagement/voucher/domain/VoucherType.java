package com.programmers.vouchermanagement.voucher.domain;

import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {
    FIXED("1", "Fixed Amount"),
    PERCENT("2", "Percent");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final String INVALID_VOUCHER_TYPE_MESSAGE =
            "Voucher type should be either fixed amount or percent discount voucher.";

    private final String menuCode;
    private final String typeName;

    VoucherType(String menuCode, String typeName) {
        this.menuCode = menuCode;
        this.typeName = typeName;
    }

    public static VoucherType findVoucherTypeByName(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(menu -> menu.isMatchingName(input))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error(INVALID_VOUCHER_TYPE_MESSAGE);
                    return new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE);
                });
    }

    public static VoucherType findVoucherTypeByCode(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(menu -> menu.isMatchingCode(input))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error(INVALID_VOUCHER_TYPE_MESSAGE);
                    return new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE);
                });
    }

    private boolean isMatchingName(String input) {
        return input.equalsIgnoreCase(this.name());
    }

    private boolean isMatchingCode(String input) {
        return Objects.equals(menuCode, input);
    }

    public boolean isPercent() {
        return this == PERCENT;
    }

    public String displayTypeName() {
        return typeName;
    }
}
