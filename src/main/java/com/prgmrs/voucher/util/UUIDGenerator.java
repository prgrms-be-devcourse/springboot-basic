package com.prgmrs.voucher.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator {
    public static UUID generateUUID() {
        return UUID.randomUUID();
    }
}
