package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum VoucherType {
    FIXED("할인액"),
    PERCENT("할인율");

    private final String description;

    VoucherType(String description) {
        this.description = description;
    }

    public static VoucherType of(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(VoucherType.class + ErrorMessageType.NOT_EXIST_VOUCHER_TYPE.getMessage()));
    }

    public String getDescription() {
        return description;
    }
}
