package com.prgmrs.voucher.util;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

import java.util.UUID;

public class UUIDConverter {

    private UUIDConverter() {
        throw new IllegalStateException("Util class");
    }

    public static UUID fromString(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new WrongRangeFormatException("type valid uuid");
        }
    }
}
