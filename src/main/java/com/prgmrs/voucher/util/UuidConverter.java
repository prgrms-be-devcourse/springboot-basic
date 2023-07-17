package com.prgmrs.voucher.util;

import com.prgmrs.voucher.exception.WrongRangeFormatException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidConverter {
    public static UUID fromString(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new WrongRangeFormatException("type valid uuid");
        }
    }
}
