package com.programmers.voucher.enums;

import com.programmers.voucher.exception.VoucherErrorCode;
import com.programmers.voucher.exception.VoucherException;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType getVoucherType(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(VoucherType -> Objects.equals(VoucherType.type, type))
                .findAny()
                .orElseThrow(() -> new VoucherException(VoucherErrorCode.NOT_SUPPORTED_TYPE));
    }
}
